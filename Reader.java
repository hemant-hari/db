import java.io.*;
import java.util.*;

class Reader {
    Table wtable;
    Db currdb;

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
            String[] hdrs_constr = sc.nextLine().split(",");
            setHeaderConstraints(key, hdrs_constr);
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

    private void setHeaderConstraints(String key, String[] hdrs_constr) {
        List<String[]> hclist = new ArrayList<String[]>();
        String[] headers = new String[hdrs_constr.length];
        for (int i=0; i<hdrs_constr.length; i++){
            String[] hcsplit = hdrs_constr[i].split("~");
            hclist.add(i, hcsplit);
            headers[i] = hcsplit[0];
        }
        wtable.setHeaders(key, headers);
        for (int i=0; i<hdrs_constr.length; i++){
            if (hclist.get(i).length != 1){
                setConstraint(hclist.get(i));
            }
        }
    }

    private void setConstraint(String[] hcsplit) {
        for (int i=1; i<hcsplit.length; i++){
            if (hcsplit[i].equals("Int")){
                wtable.setConstraint(hcsplit[0], Constraint.Int);
            }
            else if (hcsplit[i].equals("FK")){
                wtable.setFKConstraint(hcsplit[0], currdb.getTable(hcsplit[i+1]), hcsplit[i+2]);
                i+=2;
            }
        }
    }

    void setTable(Table newTable) {
        wtable = newTable;
    }

    void setDB(Db currdb) {
        this.currdb = currdb;
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
