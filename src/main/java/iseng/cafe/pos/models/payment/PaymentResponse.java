package iseng.cafe.pos.models.payment;

import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.entities.PaymentMethod;

public class PaymentResponse {
    private Integer id;

    private MenuOrder menuOrder;

    private Customer customer;

    private PaymentMethod paymentMethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MenuOrder getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(MenuOrder menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
