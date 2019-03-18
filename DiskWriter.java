import java.util.*;
import java.io.*;

class DiskWriter {
    String currdb;

    void makeDBdir(String dbname) {
        File nf = new File("dbstorage/" + dbname);
        nf.mkdirs();
    }

    void setDB(String dbname) {
        currdb = dbname;
    }

    void saveTable(Table tbl, String tblname, int tblnum) {
        try {
            Writer w = new FileWriter("dbstorage/" + currdb + "/tbl" + tblnum);
            w.write(tblname + "\n");
            w.write(tbl.getKeyCol() + '\n');
            for (String hdr : tbl.getHeaders()){
                w.write(hdr + ",");
            }
            w.write('\n');
            for (String key : tbl.getAllKeys()) {
                for (String field : tbl.getRecord(key)){
                    w.write(field + ",");
                }
                w.write('\n');
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DiskWriter prog = new DiskWriter();
        prog.run();
    }

    private void run() {
        makeDBdir("testdb");
    }
}
