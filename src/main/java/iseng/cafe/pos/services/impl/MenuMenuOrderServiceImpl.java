package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.repositories.MenuOrderRepository;
import iseng.cafe.pos.services.AdminService;
import iseng.cafe.pos.services.CustomerService;
import iseng.cafe.pos.services.MenuOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuMenuOrderServiceImpl extends CommonServiceImpl<MenuOrder, Integer> implements MenuOrderService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    public MenuMenuOrderServiceImpl(MenuOrderRepository repository) {
        super(repository);
    }

}
