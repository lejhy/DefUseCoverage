public class DefUsePair {
    FieldAccesss write;
    FieldAccesss read;

    public DefUsePair(FieldAccesss first, FieldAccesss second) {
        if (!first.getField().equals(second.getField())) throw new IllegalArgumentException("Both instructions must access the same field");
        if (first.getType() == FieldAccesss.Type.WRITE && second.getType() == FieldAccesss.Type.READ) {
            this.write = first;
            this.read = second;
        } else if (first.getType() == FieldAccesss.Type.READ && second.getType() == FieldAccesss.Type.WRITE) {
            this.write = second;
            this.read = first;
        } else {
            throw new IllegalArgumentException("One fieldAccesss has to be a READ and the other has to be a WRITE");
        }
    }

    public FieldAccesss getWrite() {
        return write;
    }

    public FieldAccesss getRead() {
        return read;
    }

    @Override
    public String toString() {
        return "{"+ write.toString()+ read.toString()+"}";
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof DefUsePair)) {
            return false;
        }
        DefUsePair anotherPair = (DefUsePair)anotherObject;
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