package iseng.cafe.pos.entities;

import javax.persistence.*;

@Table
@Entity
public class Snack extends AbstractEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "snack_type_id", nullable = false)
    private SnackType snackType;

    @ManyToOne
    @JoinColumn(name = "snack_category_id", nullable = false)
    private SnackCategory snackCategory;

    private Double price;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SnackType getSnackType() {
        return snackType;
    }

    public void setSnackType(SnackType snackType) {
        this.snackType = snackType;
    }

    public SnackCategory getSnackCategory() {
        return snackCategory;
    }

    public void setSnackCategory(SnackCategory snackCategory) {
        this.snackCategory = snackCategory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
