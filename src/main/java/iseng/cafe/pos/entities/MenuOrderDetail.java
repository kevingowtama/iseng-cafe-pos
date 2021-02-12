package iseng.cafe.pos.entities;

import javax.persistence.*;

@Table
@Entity
public class MenuOrderDetail extends AbstractEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "menu_order_id", nullable = false)
    private MenuOrder menuOrder;

    @ManyToOne
    @JoinColumn(name = "snack_id", nullable = false)
    private Snack snack;

    private Integer quantity;

    private String notes;

    private Double subTotal;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public MenuOrder getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(MenuOrder menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Snack getSnack() {
        return snack;
    }

    public void setSnack(Snack snack) {
        this.snack = snack;
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
