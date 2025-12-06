import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connexion à la base réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL introuvable !");
            e.printStackTrace();
        }
        return connection;
    }

    public void main(String[] args) {
        DBConnection dbConn = new DBConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );

        try (Connection conn = dbConn.getConnection()) {
            if (conn != null) {
                System.out.println("Test de connexion réussi !");
            }
        } catch (SQLException e) {
            System.err.println("Échec de connexion");
            e.printStackTrace();
        }
    }
}
