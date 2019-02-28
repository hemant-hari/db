import java.util.*;

class Table {
    private Map<String, Record> table = new HashMap<String, Record>();
    private Map<Integer, String> headers = new HashMap<Integer, String>();
    private int numcols;
    private int keyindex;

    Table (String key, String ... headers) {
        numcols = headers.length;
        for (int i=0; i<numcols; i++){
            this.headers.put(i, headers[i]);
            if (headers[i].equals(key)){
                keyindex = i;
            }
        }

        if (! this.headers.containsValue(key)){
            throw new Error("Mentioned key not included in list of column titles");
        }
    }

    void addRecord(String ... fields) {
        Record r = new Record(numcols);
        if (fields.length != numcols){ throw new Error("Invalid number of fields"); }
        r.setRow(fields);
        table.put(fields[keyindex], r);
    }

    public static void main(String[] args) {
        Table prog = new Table("name", "name", "gender", "petname");
        prog.run();
    }

    private void run () {
        assert(keyindex==0);
    }

}
