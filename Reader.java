import java.io.*;
import java.util.Scanner;

class Reader {
    Table wtable;

    Reader (Table writeTable) {
        this.wtable = writeTable;
    }

    void readFile(String filedir) {
        try {
            File file = new File(filedir);
            Scanner sc = new Scanner(file);
            String key = sc.nextLine();
            String[] headers = sc.nextLine().split(", ");
            wtable.setHeaders(key, headers);

            while(sc.hasNextLine()){
                if(!wtable.addRecord(sc.nextLine().split(","))){
                    throw new Error("Error adding record from file! (Duplicate key or incorrect number of columns)");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void changeTable(Table newTable) {
        wtable = newTable;
    }

    public static void main(String[] args) {
        Reader rdr = new Reader(new Table());
        rdr.run();
    }

    private void run() {
        readFile("testdb.txt");
        wtable.printTable();
        System.out.println(wtable.columnContains("petname", "Duke"));
    }
}
