import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        return null;
    }

    public Connection getDBConnection() throws SQLException {
    String url = "jdbc:postgresql://localhost:5432/product_management_db";
    String user = "product_manager_user";
    String password = "123456";

        try (
                Connection con = DriverManager.getConnection(url, user, password)){
            System.out.println("Connected !");
            return con;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
