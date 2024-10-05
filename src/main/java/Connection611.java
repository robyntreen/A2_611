import java.sql.DriverManager;
import java.sql.SQLException;

// this class establishes the connection between the back end and the database
public class Connection611 {
    public static void main(String[] args) {
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/purchase_order_db_611", "root",
                "Rct!2003")) {
            if (conn != null) {
                System.out.println(conn);
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
