package iseng.cafe.pos.models.payment;

import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.entities.PaymentMethod;

public class PaymentRequest {

    private Integer menuOrderId;

    private Integer customerId;

    private Integer paymentMethodId;

    public Integer getMenuOrderId() {
        return menuOrderId;
    }

    public void setMenuOrderId(Integer menuOrderId) {
        this.menuOrderId = menuOrderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
