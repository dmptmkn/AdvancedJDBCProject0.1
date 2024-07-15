package printer;

import java.io.PrintStream;
import java.sql.Connection;

public abstract class Printer {

    protected final Connection dbConnection;
    protected PrintStream printer;

    public Printer(Connection dbConnection) {
        if (dbConnection == null) throw new IllegalArgumentException("Database connection cannot be Null");
        this.dbConnection = dbConnection;
        init();
    }

    protected abstract void init();
    protected abstract void collectData();
    public abstract void printData();

}
