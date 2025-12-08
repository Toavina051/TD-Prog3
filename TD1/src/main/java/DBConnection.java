import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
        String url;
        String user;
        String password;

    public DBConnection() {
        this.url = "jdbc:postgresql://localhost:5432/product_management_db";
        this.user = "product_manager_user";
        this.password = "123456";
    }

    public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, user, password);
        }

}
