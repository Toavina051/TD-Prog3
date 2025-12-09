import java.sql.*;
import java.time.Instant;
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

            Category category = null;
            while (result.next()) {
                category = (new Category(
                        result.getInt("id"),
                        result.getString("name")
                ));
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        List<Product> products = new ArrayList<Product>();
        int offset = (page - 1) * size;

        String sql = "SELECT product.id, product.name, product.creation_datetime, product_category.id, product_category.name FROM product LEFT JOIN product_category ON product.id = product_category.product_id ORDER BY product.id LIMIT ? OFFSET ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, size);
            statement.setInt(2, offset);

            ResultSet result = statement.executeQuery();

            Product currentProduct = null;
            int lastProductId = -1;

            while (result.next()) {
                int productId = result.getInt("id");

                if (productId != lastProductId) {
                    currentProduct = new Product(
                            productId,
                            result.getString("name"),
                            result.getTimestamp("creation_datetime").toInstant()
                    );

                    int categoryId = result.getInt(4);
                    if (!result.wasNull()) {
                        currentProduct.setCategories(
                                new Category(categoryId, result.getString(5))
                        );
                    }

                    products.add(currentProduct);
                    lastProductId = productId;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error in getProductList: " + e.getMessage());
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax) {

        List<Product> products = new ArrayList<Product>();

        StringBuilder sql = new StringBuilder(
                "SELECT product.id, product.name, product.creation_datetime, product_category.id, product_category.name FROM product LEFT JOIN product_category ON product.id = product_category.product_id WHERE 1=1"
        );

        if (productName != null) sql.append(" AND product.name ILIKE ?");
        if (categoryName != null) sql.append(" AND product_category.name ILIKE ?");
        if (creationMin != null) sql.append(" AND product.creation_datetime >= ?");
        if (creationMax != null) sql.append(" AND product.creation_datetime <= ?");

        sql.append(" ORDER BY product.id");

        try (Connection conn = connection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (productName != null) statement.setString(paramIndex++, "%" + productName + "%");
            if (categoryName != null) statement.setString(paramIndex++, "%" + categoryName + "%");
            if (creationMin != null) statement.setTimestamp(paramIndex++, Timestamp.from(creationMin));
            if (creationMax != null) statement.setTimestamp(paramIndex++, Timestamp.from(creationMax));

            ResultSet result = statement.executeQuery();

            Product currentProduct = null;
            int lastProductId = -1;

            while (result.next()) {
                int productId = result.getInt(1);

                if (productId != lastProductId) {
                    currentProduct = new Product(
                            productId,
                            result.getString(2),
                            result.getTimestamp(3).toInstant()
                    );

                    int categoryId = result.getInt(4);
                    if (!result.wasNull()) {
                        currentProduct.setCategories(
                                new Category(categoryId, result.getString(5))
                        );
                    }

                    products.add(currentProduct);
                    lastProductId = productId;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return products;
    }


    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size) {

        List<Product> filtered = getProductsByCriteria(productName, categoryName, creationMin, creationMax);

        int from = (page - 1) * size;
        int to = Math.min(from + size, filtered.size());

        if (from >= filtered.size()) {
            return new ArrayList<>();
        }

        return filtered.subList(from, to);
    }


}
