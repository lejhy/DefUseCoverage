import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**DefUseDataCollector
 *
 * A global object that the system reports data to during execution.
 * After the execution of the program finishes, this data is then processed and the results
 * of the coverage are presented.
 *
 * Whenever a thread executes a Read or Write method, this is reported. So for example,
 * if Thread 1 does a Write on the balance, the Collector recieves:
 *
 * (w, balance, Thread1) - meaning a 'write' on balance by Thread1.
 *
 * This data is collected over the Thread's life, resulting in it's execution timeline. This is
 * done for all seperate Threads that execute.
 *
 * 	    1           2           3           4
 * T1 - r(balance), w(balance), r(balance), w(balance), ...
 * T2 - w(balance), w(balance), r(balance), r(balance), ...
 *
 * With this information, we can count the number of Def-Use pairs that exist in our tests.
 * A Def-Use pair is a write that happens before a read, with no other write to the variable
 * inbetween. Above, (T1,2),(T2,3) is one such pair.
 *
 * We also keep the actual order of execution by all threads as one, so we can figure out
 * which pairs were executed out of all the possible pairs.
 *
 * With these two, we can calculate the total coverage for that execution run, and report
 * this to the user.
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
    private Set<Instruction> instructions;
    private Map<Field, DataAccessLogEntry> fieldToLastWrite;
    private Map<InstructionPair, Boolean> instructionPairToIsCovered;

    //Lock for making a field access and report call a synchronized block
    private Lock lock = new ReentrantLock();

    //Output whenever a report is made.
    private boolean outputReports = false;
    //Output all possible pairs generated.
    private boolean outputAllPossiblePairs = true;

    public DefUseDataCollector() {
        this.instructions = new HashSet<>();
        this.fieldToLastWrite = new HashMap<>();
        this.instructionPairToIsCovered = new HashMap<>();
    }

    public void register(char readOrWrite, int lineNumber, String enclosingClassName, String fieldName, String className) {
        if (outputReports) System.out.println("Register: "+readOrWrite+", "+lineNumber+", "+enclosingClassName+", "+fieldName+", "+className);
        Field field = new Field(fieldName, className);
        Instruction newInstruction = new Instruction(readOrWrite, lineNumber, enclosingClassName, field);
        // Check for existing accesses to this field
        for (Instruction instruction: instructions) {
            if (instruction.getField().equals(newInstruction.getField())) {
                // If they are different types, means we have a new pair
                if (instruction.getType() != newInstruction.getType()) {
                    InstructionPair pair = new InstructionPair(instruction, newInstruction);
                    if (outputReports) System.out.println("New Pair: "+pair.toString());
                    instructionPairToIsCovered.put(pair, false);
                }
            }
        }
        instructions.add(newInstruction);
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
        Instruction instruction = new Instruction(readOrWrite, lineNumber, enclosingClassName, field);
        DataAccessLogEntry logEntry = new DataAccessLogEntry(instruction, threadID);

        if (instruction.getType() == Instruction.Type.WRITE) {
            // On a write simply update the map of previous writes
            fieldToLastWrite.put(field, logEntry);
        } else if (instruction.getType() == Instruction.Type.READ) {
            // On a read create a pair with the last write if it exists and if it happened on a different thread
            DataAccessLogEntry lastWrite = fieldToLastWrite.get(field);
            System.out.println(field.toString());
            System.out.println(fieldToLastWrite.keySet().toString());
            if (lastWrite != null && lastWrite.getThreadID() != logEntry.getThreadID()) {
                InstructionPair pair = new InstructionPair(lastWrite.getInstruction(), instruction);
                if (outputReports) System.out.println("Pair Covered: "+pair.toString());
                instructionPairToIsCovered.put(pair, true);
            }
        }
    }

    public void getResults() {
        int totalPairs = instructionPairToIsCovered.size();
        int coveredPairs = 0;
        for (InstructionPair pair : instructionPairToIsCovered.keySet()) {
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
