import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
	private ArrayList<DataAccessLogEntry> globalExecutionList = new ArrayList<DataAccessLogEntry>();
	private HashMap<Long, ArrayList<DataAccessLogEntry>> threadExecutionList = new HashMap<Long, ArrayList<DataAccessLogEntry>>();
	
	//Output whenever a report is made.
	private boolean outputReports = true;
	//Output all possible pairs generated.
	private boolean outputAllPossiblePairs = true;
	
	//Internal class for representing instruction pairs.
	private class InstructionPair {
		long thread1ID;
		int instruction1ID;
		long thread2ID;
		int instruction2ID;
		
		public InstructionPair (long t1ID, int i1ID, long t2ID, int i2ID) {
			thread1ID = t1ID;
			instruction1ID = i1ID;
			thread2ID = t2ID;
			instruction2ID = i2ID;
		}
		
		public String toString() {
			return "[("+thread1ID+","+instruction1ID+") ("+thread2ID+","+instruction2ID+")]";
		}
	}
	
	public DefUseDataCollector() {
		//Set up data structures.
		// - One that holds execution order as a whole.
		// - Another that holds execution order for each specific thread.
		
	}
	
	/**
	 * Report a read/write access on a variable by a Thread.
	 * @param readOrWrite 'r' for read, 'w' for write
	 * @param variable the name of the variable accessed
	 * @param threadID the ID of the Thread that did the access
	 */
	 public synchronized void report(char readOrWrite, String variable, long threadID) {
		int instructionID;
		ArrayList<DataAccessLogEntry> list = threadExecutionList.get(threadID);
		
		//Figures out what ID the instruction is for this particular thread. Also initialises the list if this is the first instruction.
		if (list == null) {
			list = new ArrayList<DataAccessLogEntry>();
			threadExecutionList.put(threadID, list);
			instructionID = 0;
		}
		else {
			instructionID = list.size();
		}
		
		//Create a log entry with the given data.
		DataAccessLogEntry log = new DataAccessLogEntry(readOrWrite, variable, threadID, instructionID);
		
		//Checks that the readOrWrite is valid.
		if (readOrWrite == 'r' || readOrWrite == 'w') {
			if (outputReports)
				System.out.println("Report made, data reported: "+log.toString());
			
			//Add log entry to relevant data structures.
			globalExecutionList.add(log);
			list.add(log);
		}
		else {
			System.out.println("Invalid report made, data reported: "+log.toString());
		}
	}
	
	public void getResults() {
		ArrayList<InstructionPair> list = generatePairs();
		
		if (outputAllPossiblePairs) {
			System.out.println("Possible pair list size: "+list.size());
			for (InstructionPair p : list) {
				System.out.println(p.toString());
			}
		}
	}
	
	private ArrayList<InstructionPair> generatePairs() {
		ArrayList<InstructionPair> pairList = new ArrayList<InstructionPair>();
		Set<Long> keys = threadExecutionList.keySet();
		//For each thread's execution list...
		for (Long key : keys) {
			//Get this thread's list
			ArrayList<DataAccessLogEntry> currentList = threadExecutionList.get(key);
			//Compare to all other thread lists...
			for (long otherKey : keys) {
				if (key != otherKey) {
					//Get the other thread's list
					ArrayList<DataAccessLogEntry> otherList = threadExecutionList.get(otherKey);
					//Now go through the current thread's list, looking for a write.
					for (DataAccessLogEntry curEntry : currentList) {
						//Found a write
						if (curEntry.getAccessType() == 'w') {
							//Look through the other thread list, looking for reads to pair with this write.
							for (DataAccessLogEntry otherEntry : otherList) {
								if (curEntry.getVariableName() == otherEntry.getVariableName()) {
									if (otherEntry.getAccessType() == 'r') {
										//We found a match!
										InstructionPair i = new InstructionPair(curEntry.getThreadID(), curEntry.getInstructionID(), 
												                                otherEntry.getThreadID(), otherEntry.getInstructionID());
										pairList.add(i);
									}
								}
							}
						}
					}
				}
			}
		}
		return pairList;
	}
}
