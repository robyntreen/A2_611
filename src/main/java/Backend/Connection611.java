package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection611 {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/purchase_order_db_611";
    private static final String USER = "root";
    private static final String PASSWORD = "Rct!2003";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
