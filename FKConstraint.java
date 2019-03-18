class FKConstraint {
    private Table ftable;
    private String ftbl_col;

    FKConstraint(Table ftable, String ftbl_col){
        this.ftable = ftable;
        this.ftbl_col = ftbl_col;
    }

    Table getFTable() {
        return ftable;
    }

    String getFTableColumn() {
        return ftbl_col;
    }
}
