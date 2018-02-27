public class InstructionPair {
    Instruction i1;
    Instruction i2;

    public InstructionPair (Instruction i1, Instruction i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    public Instruction getI1() {
        return i1;
    }

    public Instruction getI2() {
        return i2;
    }

    @Override
    public String toString() {
        return "{"+i1.toString()+i2.toString()+"}";
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof InstructionPair)) {
            return false;
        }
        InstructionPair anotherPair = (InstructionPair)anotherObject;
        if (!anotherPair.getI1().equals(i1)) {
            return false;
        }
        if (!anotherPair.getI2().equals(i2)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result =  i1.hashCode();
        result = 37 * result + i2.hashCode();
        return result;
    }
}