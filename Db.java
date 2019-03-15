import java.util.*;

class Db {
    private Map<String, Table> database = new LinkedHashMap<String, Table>();
    private Table currTable;

    void addTable(String name){
        database.put(name, new Table());
    }

    void printTableNames() {
        for (String key : database.keySet()) {
            System.out.println(key);
        }
    }

    void loadTable(String filedir){
        Reader rdr = new Reader(currTable);
        rdr.readFile(filedir);
    }

    void showTable(String tablename){
        database.get(tablename).printTable();
    }

    void setTable(String tablename){
        currTable = database.get(tablename);
    }

    boolean keyExists(String stringToCheck){
        return currTable.checkKey(stringToCheck);
    }

    boolean tableContains(String column, String value) {
        return currTable.columnContains(column, value);
    }

    Set<String> keysWhichContain(String column, String value) {
        return currTable.keysWhichContain(column, value);
    }

    void modifyEntry(String key, String column, String new_val) {
        currTable.modifyRecord(key, column, new_val);
    }

    void displaySubset(Set<String> keySubset) {
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
        printTableNames();
        addTable("pets");
        printTableNames();
        setTable("pets");
        loadTable("testdb.txt");
        showTable("pets");
        displaySubset(keysWhichContain("pgender", "M"));
    }
}
