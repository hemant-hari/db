import java.util.*;

class Db {
    private Map<String, Table> database = new LinkedHashMap<String, Table>();
    private Table currTable;
    private Display dsp;

    Db() {
        dsp = new Display();
    }

    void addTable(String name){
        database.put(name, new Table());
    }

    void addEntry(String ... fields) {
        currTable.addRecord(fields);
    }

    void addColumn(String headername) {
        currTable.addCol(headername);
    }

    Table getTable(String tablename) {
        return database.get(tablename);
    }

    String[] getTableNames() {
        Set<String> tblnames = database.keySet();
        return tblnames.toArray(new String[tblnames.size()]);
    }

    //readFile returns the tablename
    void loadTable(String filedir){
        Table tbl = new Table();
        Reader rdr = new Reader(tbl);
        database.put(rdr.readFile(filedir), tbl);
    }

    void showTable(String tablename){
        dsp.setTable(database.get(tablename));
        dsp.displayTable();
    }

    void setTable(String tablename){
        currTable = database.get(tablename);
        dsp.setTable(currTable);
    }

    boolean keyExists(String stringToCheck){
        return currTable.checkKey(stringToCheck);
    }

    boolean tableContains(String column, String value) {
        return currTable.columnContains(column, value);
    }

    Set<String> recordsWhichContain(String column, String value) {
        return currTable.keysWhichContain(column, value);
    }

    void modifyEntry(String key, String column, String new_val) {
        currTable.modifyRecord(key, column, new_val);
    }

    void displaySubset(Set<String> keySubset) {
        dsp.displaySubset(keySubset);
    }

    void saveDB(String dbname) {
        DiskWriter writer = new DiskWriter();
        int i=0;
        writer.makeDBdir(dbname);
        writer.setDB(dbname);
        for (String tblname : database.keySet()){
            writer.saveTable(database.get(tblname), tblname, i);
            i++;
        }
    }

    void loadDB(String dbname) {
        Reader rdr = new Reader(null);
        rdr.setDB(this);
        int numtables = rdr.numTables(dbname);
        if (numtables == 0) { throw new Error("No tables in directory"); }
        database.clear();
        currTable = null;
        for (int i=0; i<numtables; i++) {
            Table tbl = new Table();
            rdr.setTable(tbl);
            database.put(rdr.readFile("dbstorage/" + dbname + "/tbl" + i), tbl);
        }
    }

    public static void main(String[] args) {
        Db prog = new Db();
        prog.run();
    }

    private void run() {
        loadTable("testdb.txt");
        setTable("pets");
        showTable("pets");
        displaySubset(recordsWhichContain("pgender", "M"));
        saveDB("testingdb");
        Reader rdr = new Reader(null);
        assert(rdr.numTables("testingdb") == 1);
        loadDB("testingdb");
        assert(getTableNames().length == 1);
        setTable("pets");
        addColumn("type");
        showTable("pets");
        saveDB("testingdb");
        loadDB("fktestingdb");
        showTable("owners");
        showTable("pets");
    }
}
