
public class DataAccessLogEntry {

    long threadID;
    FieldAccesss fieldAccesss;

    public DataAccessLogEntry(FieldAccesss fieldAccesss, long threadID) {
        this.threadID = threadID;
        this.fieldAccesss = fieldAccesss;
    }

    public long getThreadID() {
        return threadID;
    }

    public FieldAccesss getFieldAccesss() {
       return fieldAccesss;
    }

    public String toString() {
        return "<"+threadID+","+ fieldAccesss +">";
    }

    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof DataAccessLogEntry)) {
            return false;
        }
        DataAccessLogEntry anotherLogEntry = (DataAccessLogEntry)anotherObject;
        if (anotherLogEntry.getThreadID() != threadID) {
            return false;
        }
        if (!anotherLogEntry.getFieldAccesss().equals(fieldAccesss)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result =  (int)(threadID ^ (threadID >>> 32));
        result = 37 * result + fieldAccesss.hashCode();
        return result;
    }
}
