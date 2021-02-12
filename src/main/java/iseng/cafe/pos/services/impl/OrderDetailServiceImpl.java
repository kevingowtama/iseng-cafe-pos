package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.MenuOrderDetail;
import iseng.cafe.pos.repositories.OrderDetailRepository;
import iseng.cafe.pos.services.OrderDetailService;
import iseng.cafe.pos.services.OrderService;
import iseng.cafe.pos.services.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends CommonServiceImpl<MenuOrderDetail, Integer> implements OrderDetailService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private SnackService snackService;

    public OrderDetailServiceImpl(OrderDetailRepository repository) {
        super(repository);
    }

}
