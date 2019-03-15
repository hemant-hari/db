import java.util.*;

class Db {
    private Map<String, Table> database = new LinkedHashMap<String, Table>();

    void addTable(String name){
        database.put(name, new Table());
    }

    void loadTable(String tablename, String filedir){
        Reader rdr = new Reader(database.get(tablename));
        rdr.readFile(filedir);
    }

    void showTable(String tablename){
        database.get(tablename).printTable();
    }

    boolean keyExists(String tablename, String stringToCheck){
        return database.get(tablename).checkKey(stringToCheck);
    }

    boolean tableContains(String tablename, String column, String value) {
        return database.get(tablename).columnContains(column, value);
    }

    Set<String> keysWhichContain(String tablename, String column, String value) {
        return database.get(tablename).keysWhichContain(column, value);
    }

    void modifyEntry(String tablename, String key, String column, String new_val) {
        database.get(tablename).modifyRecord(key, column, new_val);
    }

    void displaySubset(String tablename, Set<String> keySubset) {
        Table currTable = database.get(tablename);
        currTable.printHeaders();
        for (String key : keySubset){
            currTable.printField(key);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Db prog = new Db();
        prog.run();
    }

    private void run() {
        addTable("pets");
        loadTable("pets", "testdb.txt");
        showTable("pets");
        displaySubset("pets", keysWhichContain("pets","pgender", "M"));
    }
}
