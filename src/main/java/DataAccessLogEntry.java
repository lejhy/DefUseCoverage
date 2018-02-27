
public class DataAccessLogEntry {

    long threadID;
    Instruction instruction;

    public DataAccessLogEntry(Instruction instruction, long threadID) {
        this.threadID = threadID;
        this.instruction = instruction;
    }

    public long getThreadID() {
        return threadID;
    }

    public Instruction getInstruction() {
       return instruction;
    }

    public String toString() {
        return "<"+threadID+","+instruction+">";
    }

    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof DataAccessLogEntry)) {
            return false;
        }
        DataAccessLogEntry anotherLogEntry = (DataAccessLogEntry)anotherObject;
        if (anotherLogEntry.getThreadID() != threadID) {
            return false;
        }
        if (!anotherLogEntry.getInstruction().equals(instruction)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result =  (int)(threadID ^ (threadID >>> 32));
        result = 37 * result + instruction.hashCode();
        return result;
    }
}
