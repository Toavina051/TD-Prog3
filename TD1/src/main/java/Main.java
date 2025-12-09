import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        DBConnection dbConnection = new DBConnection();

        // --- Test explicite de la connexion pour afficher l'erreur si elle existe ---
        try (Connection test = dbConnection.getConnection()) {
            System.out.println("Connexion à la BDD OK : ");
        } catch (SQLException e) {
            System.err.println("Impossible d'obtenir la connexion. Vérifie : URL, user, mot de passe et que PostgreSQL tourne.");
            e.printStackTrace();
            return; // on arrête si la connexion échoue
        }

        // --- Si on arrive ici la connexion fonctionne ---
        DataRetriever dr = new DataRetriever(dbConnection);

        System.out.println("\n=== ALL CATEGORIES ===");
        dr.getAllCategories().forEach(System.out::println);

        System.out.println("\n=== PAGINATION page 1 size 2 ===");
        dr.getProductList(1, 2).forEach(System.out::println);

        System.out.println("\n=== FILTER BY NAME ('lap') ===");
        dr.getProductsByCriteria("lap", null, null, null)
                .forEach(System.out::println);

        System.out.println("\n=== FILTER + DATE RANGE ===");
        dr.getProductsByCriteria(
                null,
                "Electronics",
                Instant.parse("2023-12-30T00:00:00Z"),
                Instant.parse("2024-12-31T00:00:00Z")
        ).forEach(System.out::println);
    }
}
