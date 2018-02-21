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
	
	public DefUseDataCollector() {
		//Set up data structures.
	}
	
	public void report(char readOrWrite, String variable, long threadID) {
		//Takes the data given and stores it.
	}
	
	public void getResults() {
		
	}
}
