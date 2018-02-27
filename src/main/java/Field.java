public class Field {
    private String fieldName;
    private String className;

    public Field (String fieldName, String className) {
        this.className = className;
        this.fieldName = fieldName;
    }

    public String getClassName() {
        return className;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "("+className+","+fieldName+")";
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof Field)) {
            return false;
        }
        Field anotherField = (Field)anotherObject;
        if (!anotherField.getClassName().equals(className)) {
            return false;
        }
        if (!anotherField.getFieldName().equals(fieldName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result =  fieldName.hashCode();
        result = 37 * result + className.hashCode();
        return result;
    }
}
