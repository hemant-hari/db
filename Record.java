class Record {
    private String[] fields;
    private int numcols;

    Record (int numcols) {
        this.numcols = numcols;
        fields =  new String[numcols];
    }

    boolean set(String ... values) {
        if (values.length != numcols) { return false; }
        for (int i=0; i<numcols; i++) { fields[i] = values[i];}
        return true;
    }

    int length() {
        return numcols;
    }

    String get(int index) {
        return fields[index]
    }

    public static void main(String[] args) {
        Record prog = new Record(5);
        prog.run();
    }

    private void run() {
        testSet();
    }

    private void testSet() {
        assert(set("Hello","World!","Hello","World!","Dang!"));
        assert(!set("Hello","World!","Hello","World!","Dang!","ERROR!"));
        assert(!set("Hello","World!","Hello","World!"));
    }
}
