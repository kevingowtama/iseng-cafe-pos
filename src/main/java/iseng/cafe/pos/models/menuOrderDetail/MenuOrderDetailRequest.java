package iseng.cafe.pos.models.menuOrderDetail;

import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.entities.Snack;

public class MenuOrderDetailRequest {

    private Integer menuOrderId;

    private Integer snackId;

    private Integer quantity;

    private String notes;

    private Double subTotal;

    public Integer getMenuOrderId() {
        return menuOrderId;
    }

    public void setMenuOrderId(Integer menuOrderId) {
        this.menuOrderId = menuOrderId;
    }

    public Integer getSnackId() {
        return snackId;
    }

    public void setSnackId(Integer snackId) {
        this.snackId = snackId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
