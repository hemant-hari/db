import java.io.*;
import java.util.Scanner;

class Reader {
    Table wtable;

    Reader (Table writeTable) {
        this.wtable = writeTable;
    }

    int numTables(String DBname) {
        File f = new File("dbstorage/" + DBname + "/");
        File[] filelist = f.listFiles();
        return filelist.length;
    }

    String readFile(String filedir) {
        try {
            File file = new File(filedir);
            Scanner sc = new Scanner(file);
            String tablename = sc.nextLine();
            String key = sc.nextLine();
            String[] headers = sc.nextLine().split(",");
            wtable.setHeaders(key, headers);

            while(sc.hasNextLine()){
                if(!wtable.addRecord(sc.nextLine().split(","))){
                    throw new Error("Error adding record from file! (Duplicate key or incorrect number of columns)");
                }
            }
            return tablename;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    void setTable(Table newTable) {
        wtable = newTable;
    }

    public static void main(String[] args) {
        Reader rdr = new Reader(new Table());
        rdr.run();
    }

    private void run() {
        readFile("testdb.txt");
        System.out.println(wtable.columnContains("petname", "Duke"));
    }
}
