import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**DefUseDataCollector
 *
 * A global object that the system reports data to during execution.
 * After the execution of the program finishes, this data is then processed and the results
 * of the coverage are presented.
 *
 * To use this class, call the get() function statically, then use report() on that object, like so:
 * 		DefUseDataCollector.get().report();
 *
 * You can call getResults() when the system has finished executing to get the results.
 */

public class DefUseDataCollector {

    //Singleton pattern, use get() to get the global instance of this class.
    private static DefUseDataCollector collector = new DefUseDataCollector();
    public static DefUseDataCollector get() { return collector; }

    //Data Structure fields
    private Set<FieldAccesss> fieldAccesses;
    private Map<Field, DataAccessLogEntry> fieldToLastWrite;
    private Map<DefUsePair, Boolean> instructionPairToIsCovered;

    //Lock for making a field access and report call a synchronized block
    private Lock lock = new ReentrantLock();

    //Output whenever a report is made.
    private boolean outputReports = false;
    //Output all possible pairs generated.
    private boolean outputAllPossiblePairs = true;

    public DefUseDataCollector() {
        this.fieldAccesses = new HashSet<>();
        this.fieldToLastWrite = new HashMap<>();
        this.instructionPairToIsCovered = new HashMap<>();
    }

    public void register(char readOrWrite, int lineNumber, String enclosingClassName, String fieldName, String className) {
        if (outputReports) System.out.println("Register: "+readOrWrite+", "+lineNumber+", "+enclosingClassName+", "+fieldName+", "+className);
        Field field = new Field(fieldName, className);
        FieldAccesss newFieldAccesss = new FieldAccesss(readOrWrite, lineNumber, enclosingClassName, field);
        // Check for existing accesses to this field
        for (FieldAccesss fieldAccesss : fieldAccesses) {
            if (fieldAccesss.getField().equals(newFieldAccesss.getField())) {
                // If they are different types, means we have a new pair
                if (fieldAccesss.getType() != newFieldAccesss.getType()) {
                    DefUsePair pair = new DefUsePair(fieldAccesss, newFieldAccesss);
                    if (outputReports) System.out.println("New Pair: "+pair.toString());
                    instructionPairToIsCovered.put(pair, false);
                }
            }
        }
        fieldAccesses.add(newFieldAccesss);
    }

    /**
     * Report a read/write access on a field by a Thread.
     * @param readOrWrite 'r' for read, 'w' for write
     * @param lineNumber the line number of this access
     * @param enclosingClassName the name of the class where this access happened
     * @param fieldName the name of the field accessed
     * @param className the name of the class this field belongs to
     * @param threadID the ID of the Thread that did the access
     */
    public synchronized void report(char readOrWrite, int lineNumber, String enclosingClassName, String fieldName, String className, long threadID) {
        if (outputReports) System.out.println("Report: "+readOrWrite+", "+lineNumber+", "+enclosingClassName+", "+fieldName+", "+className);
        Field field = new Field(fieldName, className);
        FieldAccesss fieldAccesss = new FieldAccesss(readOrWrite, lineNumber, enclosingClassName, field);
        DataAccessLogEntry logEntry = new DataAccessLogEntry(fieldAccesss, threadID);

        if (fieldAccesss.getType() == FieldAccesss.Type.WRITE) {
            // On a write simply update the map of previous writes
            fieldToLastWrite.put(field, logEntry);
        } else if (fieldAccesss.getType() == FieldAccesss.Type.READ) {
            // On a read create a pair with the last write if it exists and if it happened on a different thread
            DataAccessLogEntry lastWrite = fieldToLastWrite.get(field);
            if (lastWrite != null && lastWrite.getThreadID() != logEntry.getThreadID()) {
                DefUsePair pair = new DefUsePair(lastWrite.getFieldAccesss(), fieldAccesss);
                if (outputReports) System.out.println("Pair Covered: "+pair.toString());
                instructionPairToIsCovered.put(pair, true);
            }
        }
    }

    public void getResults() {
        int totalPairs = instructionPairToIsCovered.size();
        int coveredPairs = 0;
        for (DefUsePair pair : instructionPairToIsCovered.keySet()) {
            boolean isCovered = instructionPairToIsCovered.get(pair);
            if (outputAllPossiblePairs) System.out.println(pair.toString() + "; covered: " + isCovered);
            if (isCovered) coveredPairs++;
        }
        System.out.println("Covered "+coveredPairs+" out of "+totalPairs);
        System.out.println("Total coverage: "+String.format("%.2f", (float)coveredPairs/(float)totalPairs*100)+"%");
    }

    public Lock getLock() {
        return lock;
    }
}
