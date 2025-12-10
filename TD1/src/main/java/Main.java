import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        DBConnection dbConnection = new DBConnection();

        try (Connection test = dbConnection.getConnection()) {
            System.out.println("Connexion à la BDD OK : ");
        } catch (SQLException e) {
            System.err.println("Impossible d'obtenir la connexion. Vérifie : URL, user, mot de passe et que PostgreSQL tourne.");
            e.printStackTrace();
            return;
        }

        DataRetriever dr = new DataRetriever(dbConnection);

        System.out.println("\n=== ALL CATEGORIES ===");
        dr.getAllCategories().forEach(System.out::println);

        System.out.println("\n=== ProductList ===");
        dr.getProductList(1, 10).forEach(System.out::println);
        dr.getProductList(1, 5).forEach(System.out::println);
        dr.getProductList(1, 3).forEach(System.out::println);
        dr.getProductList(2, 2).forEach(System.out::println);

        System.out.println("\n=== ProductByCriteria ===");
        dr.getProductsByCriteria("Dell", null, null, null)
                .forEach(System.out::println);
        dr.getProductsByCriteria(null, "info", null, null)
                .forEach(System.out::println);
        dr.getProductsByCriteria("iphone", "mobile", null, null)
                .forEach(System.out::println);
        dr.getProductsByCriteria(null, null, Instant.ofEpochSecond(2024 - 02 - 01), Instant.ofEpochSecond(2024 - 03 - 01))
                .forEach(System.out::println);
        dr.getProductsByCriteria("samsung", "bureau", null, null)
                .forEach(System.out::println);
        dr.getProductsByCriteria("sony", "informatique", null, null)
                .forEach(System.out::println);
        dr.getProductsByCriteria(null, "audio", Instant.ofEpochSecond(2024 - 01 - 01), Instant.ofEpochSecond(2024 - 12 - 01))
                .forEach(System.out::println);
        dr.getProductsByCriteria(null, null, null, null)
                .forEach(System.out::println);
        }
    }
