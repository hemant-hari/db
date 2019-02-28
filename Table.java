import java.util.*;

class Table {
    private Map<String, Record> table = new HashMap<String, Record>();
    private Map<String, Integer> headers = new HashMap<String, Integer>();
    private int numcols;
    private int keyindex;

    Table (String key, String ... headers) {
        numcols = headers.length;
        for (int i=0; i<numcols; i++){
            this.headers.put(headers[i], i);
            if (headers[i].equals(key)){
                keyindex = i;
            }
        }

        if (! this.headers.containsKey(key)){
            throw new Error("Mentioned key not included in list of column titles");
        }
    }

    String getField(String key_val, String column) {
        checkValidField(key_val, column);
        return table.get(key_val).getField(headers.get(column));
    }

    boolean addRecord(String ... fields) {
        Record r = new Record(numcols);
        if (fields.length != numcols){ return false; }
        if (table.containsKey(fields[keyindex])) { return false; }
        r.setRow(fields);
        table.put(fields[keyindex], r);
        return true;
    }

    boolean modifyRecord(String key_val, String column, String new_val) {
        try { checkValidField(key_val, column); }
        catch (Error err) { return false; }
        if (headers.get(column) == keyindex) { return false; }
        table.get(key_val).setField(new_val, headers.get(column));
        return true;
    }

    private void checkValidField(String key_val, String column){
        if (! headers.containsKey(column)) {
            throw new Error("Mentioned header not included in list of column titles");
        }
        if (! table.containsKey(key_val)) {
            throw new Error("Mentioned key not contained in list of records");
        }
    }

    public static void main(String[] args) {
        Table prog = new Table("petname", "ownername", "gender", "petname");
        prog.run();
    }

    private void run () {
        testRecordAdd();
        testGet();
        testModifyRecord();
    }

    private void testRecordAdd() {
        assert(addRecord("Hemant", "M", "Bruno"));
        assert(addRecord("Amy", "F", "Brigitte"));
        assert(addRecord("Bob", "M", "Ruby"));
        assert(!addRecord("Oh no!", "Bob", "M", "Ruby"));
    }

    private void testGet() {
        /*
        System.out.println(getField("Bob", "petname"));
        System.out.println(getField("Hemant", "petname"));
        System.out.println(getField("Amy", "petname"));
        System.out.println(getField("Amy", "gender"));
        */
        System.out.println(getField("Bruno", "ownername"));
    }

    private void testModifyRecord() {
        assert(modifyRecord("Bruno", "ownername", "Vineeth"));
        System.out.println(getField("Bruno", "ownername"));
    }
}
