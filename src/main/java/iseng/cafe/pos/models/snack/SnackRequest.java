package iseng.cafe.pos.models.snack;

import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.entities.SnackType;

public class SnackRequest {

    private Integer id;

    private String name;

    private Integer snackTypeId;

    private Integer snackCategoryId;

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

    public Integer getSnackTypeId() {
        return snackTypeId;
    }

    public void setSnackTypeId(Integer snackTypeId) {
        this.snackTypeId = snackTypeId;
    }

    public Integer getSnackCategoryId() {
        return snackCategoryId;
    }

    public void setSnackCategoryId(Integer snackCategoryId) {
        this.snackCategoryId = snackCategoryId;
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
