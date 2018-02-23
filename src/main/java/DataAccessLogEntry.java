
public class DataAccessLogEntry {

	private char accessType;
	String variableName;
	long threadID;
	int instructionID;
	
	public DataAccessLogEntry(char readOrWrite, String variable, long threadID, int instructionID) {
		accessType = readOrWrite;
		variableName = variable;
		this.threadID = threadID;
		this.instructionID = instructionID;
	}

	public char getAccessType() {
		return accessType;
	}

	public String getVariableName() {
		return variableName;
	}

	public long getThreadID() {
		return threadID;
	}
	
	public int getInstructionID() {
		return instructionID;
	}
	
	public String toString() {
		return "[("+threadID+","+instructionID+"): '"+accessType+"', var: "+variableName+"]";
	}
	
}
