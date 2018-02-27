public class Instruction {
    public enum Type {
        READ, WRITE
    }

    private Type type;
    private int lineNumber;
    private String enclosingClassName;
    private Field field;

    public Instruction (Type type, int lineNumber, String enclosingClassName, Field field) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.enclosingClassName = enclosingClassName;
        this.field = field;
    }

    public Instruction (char accessType, int lineNumber, String enclosingClassName, Field field) {
        Type type;
        if (accessType == 'w') type = Type.WRITE;
        else if (accessType == 'r') type = Type.READ;
        else throw new IllegalArgumentException();
        this.type = type;
        this.lineNumber = lineNumber;
        this.enclosingClassName = enclosingClassName;
        this.field = field;
    }

    public Type getType() {
        return type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getEnclosingClassName() {
        return enclosingClassName;
    }

    public Field getField() {
        return field;
    }

    @Override
    public String toString() {
        return "["+type+","+lineNumber+","+field.toString()+"]";
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof Instruction)) {
            return false;
        }
        Instruction anotherInstruction = (Instruction)anotherObject;
        if (!anotherInstruction.getType().equals(type)) {
            return false;
        }
        if (anotherInstruction.getLineNumber() != lineNumber) {
            return false;
        }
        if (!anotherInstruction.getEnclosingClassName().equals(enclosingClassName)) {
            return false;
        }
        if (!anotherInstruction.getField().equals(field)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result =  type.hashCode();
        result = 37 * result + lineNumber;
        result = 37 * result + enclosingClassName.hashCode();
        result = 37 * result + field.hashCode();
        return result;
    }
}
