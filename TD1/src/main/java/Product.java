import java.time.Instant;

public class Product {

    private int id;
    private String name;
    private Instant creationDatetime;
    private Category categories;

    public Product(int id, String name, Instant creationDatetime) {
        this.id = id;
        this.name = name;
        this.creationDatetime = creationDatetime;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Instant getCreationDatetime() {
        return creationDatetime;
    }
    public Category getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDatetime=" + creationDatetime +
                ", categories=" + categories +
                '}';
    }
}
