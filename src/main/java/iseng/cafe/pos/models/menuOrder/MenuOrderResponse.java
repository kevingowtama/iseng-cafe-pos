package iseng.cafe.pos.models.menuOrder;

import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.models.admin.AdminResponse;
import iseng.cafe.pos.models.customer.CustomerResponse;

public class MenuOrderResponse {
    private Integer id;

    private Double totalCost;

    private String description;

    private CustomerResponse customer;

    private AdminResponse admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public AdminResponse getAdmin() {
        return admin;
    }

    public void setAdmin(AdminResponse admin) {
        this.admin = admin;
    }

}
