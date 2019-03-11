class Record {
    private String[] fields;
    private int numcols;

    Record (int numcols) {
        this.numcols = numcols;
        fields =  new String[numcols];
    }

    void resetRow() {
        for (int i=0; i<numcols; i++) {
            fields[i] = null;
        }
    }

    void setField(String value, int index) {
        fields[index] = value;
    }

    boolean setRow(String ... values) {
        if (values.length != numcols) { return false; }
        for (int i=0; i<numcols; i++) { fields[i] = values[i];}
        return true;
    }

    int length() {
        return numcols;
    }

    String getField(int index) {
        return fields[index];
    }

    String[] getAllFields() {
        return fields;
    }

    public static void main(String[] args) {
        Record prog = new Record(5);
        prog.run();
    }

    private void run() {
        testSet();
        testReset();
    }

    private void testSet() {
        assert(setRow("Hello","World!","Hello","World!","Dang!"));
        assert(!setRow("Hello","World!","Hello","World!","Dang!","ERROR!"));
        assert(!setRow("Hello","World!","Hello","World!"));
    }

    private void testReset() {
        resetRow();
        assert(getField(1) == null);
    }
}
