import java.sql.*;

public class DBase {
    /**
     * @param args
     * @throws ClassNotFoundException
     */

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/j2e_database";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
