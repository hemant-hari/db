import java.util.*;

class Display {
    Table currTable;

    void setTable(Table dTable) {
        currTable = dTable;
    }

    void displayTable() {
        printHeaders();
        for (String key : currTable.getAllKeys()){
            printRecord(key);
        }
        System.out.println();
    }

    void printHeaders() {
        String[] headers = currTable.getHeaders();
        for (String element : headers) {
            System.out.print(String.format("%-10s %s" , element, " | " ));
        }
        System.out.println();
        System.out.println();
    }

    void printRecord(String key) {
        String[] fields = currTable.getRecord(key);
        for (String element : fields) {
            System.out.print(String.format("%-10s %s" , element, " | " ));
         }
        System.out.println();
    }

    void displaySubset(Set<String> keySubset) {
        printHeaders();
        for (String key : keySubset){
            printRecord(key);
        }
        System.out.println();
    }
}
