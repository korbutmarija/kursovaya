package Server;

import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class DataTable {
    protected Statement stmt;
    protected DBConnection mdbc;

    public DataTable(Statement stmt, DBConnection mdbc) {
        this.stmt = stmt;
        this.mdbc = mdbc;
    }

    public String quotate(String content) {
        return "'" + content + "'";
    }
}
