package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.repositories.OrderRepository;
import iseng.cafe.pos.services.AdminService;
import iseng.cafe.pos.services.CustomerService;
import iseng.cafe.pos.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends CommonServiceImpl<MenuOrder, Integer> implements OrderService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }

}
