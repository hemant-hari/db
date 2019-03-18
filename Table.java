import java.util.*;

class Table {
    private Map<String, Record> table = new LinkedHashMap<String, Record>();
    private Map<String, Integer> headers = new LinkedHashMap<String, Integer>();
    private String key_col;
    private int numcols;
    private int keyindex;

    void setHeaders(String key, String ... headers) {
        numcols = headers.length;
        for (int i=0; i<numcols; i++){
            this.headers.put(headers[i], i);
            if (headers[i].equals(key)){
                keyindex = i;
            }
        }
        key_col = key;

        if (! this.headers.containsKey(key)){
            throw new Error("Mentioned key not included in list of column titles");
        }
    }

    String getKeyCol() {
        return key_col;
    }

    void addCol(String headername) {
        headers.put(headername, numcols);
        numcols++;
        for (String key : table.keySet()) {
            table.replace(key, table.get(key).addField());
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

    boolean checkKey(String key) {
        return table.containsKey(key);
    }

    //The if statement gets the Record and then finds the column index using
    //the headers map
    boolean columnContains(String column, String value) {
        for (String key : table.keySet()){
            if (value.equals(table.get(key).getField(headers.get(column)))){
                return true;
            }
        }
        return false;
    }

    Set<String> keysWhichContain(String column, String value) {
        Set<String> retSet = new LinkedHashSet<String>();
        for (String key : table.keySet()){
            if (value.equals(table.get(key).getField(headers.get(column)))){
                retSet.add(key);
            }
        }
        return retSet;
    }

    String[] getHeaders(){
        Set<String> ks = headers.keySet();
        return ks.toArray(new String[ks.size()]);
    }

    String[] getRecord(String key) {
        return table.get(key).getAllFields();
    }

    Set<String> getAllKeys() {
        return table.keySet();
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
        Table prog = new Table();
        prog.setHeaders("ownername", "ownername", "gender", "petname");
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
        System.out.println(getField("Amy", "petname"));
    }

    private void testModifyRecord() {
        assert(modifyRecord("Amy", "petname", "Brig"));
        System.out.println(getField("Amy", "petname"));
    }
}
