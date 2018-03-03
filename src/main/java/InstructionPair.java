public class InstructionPair {
    Instruction write;
    Instruction read;

    public InstructionPair (Instruction i1, Instruction i2) {
        if (!i1.getField().equals(i2.getField())) throw new IllegalArgumentException("Both instructions must access the same field");
        if (i1.getType() == Instruction.Type.WRITE && i2.getType() == Instruction.Type.READ) {
            this.write = i1;
            this.read = i2;
        } else if (i1.getType() == Instruction.Type.READ && i2.getType() == Instruction.Type.WRITE) {
            this.write = i2;
            this.read = i1;
        } else {
            throw new IllegalArgumentException("One instruction has to be a READ and the other has to be a WRITE");
        }
    }

    public Instruction getWrite() {
        return write;
    }

    public Instruction getRead() {
        return read;
    }

    @Override
    public String toString() {
        return "{"+ write.toString()+ read.toString()+"}";
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof InstructionPair)) {
            return false;
        }
        InstructionPair anotherPair = (InstructionPair)anotherObject;
        if (!anotherPair.getWrite().equals(write)) {
            return false;
        }
        if (!anotherPair.getRead().equals(read)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result =  write.hashCode();
        result = 37 * result + read.hashCode();
        return result;
    }
}