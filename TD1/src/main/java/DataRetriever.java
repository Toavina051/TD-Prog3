import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private DBConnection connection;

    public DataRetriever(DBConnection connection) {
        this.connection = connection;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();

        String sql = "select id, name FROM product_category";

        try (Connection conn = connection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                categories.add(new Category(
                        result.getInt("id"),
                        result.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<Product>();
        int offset = (page - 1) * size;

        String sql = "SELECT id, name, price, creation_datetime FROM product ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, size);
            statement.setInt(2, offset);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                products.add(new Product(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getDouble("price"),
                        result.getTimestamp("creation_datetime").toInstant()
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return products;
    }
}
