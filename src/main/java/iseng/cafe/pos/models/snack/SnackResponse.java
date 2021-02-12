package iseng.cafe.pos.models.snack;

import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.entities.SnackType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class SnackResponse {
    private Integer id;

    private String name;

    private SnackType snackType;

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
