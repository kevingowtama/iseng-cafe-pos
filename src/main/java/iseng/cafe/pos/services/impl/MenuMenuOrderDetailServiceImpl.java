package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.MenuOrderDetail;
import iseng.cafe.pos.repositories.MenuOrderDetailRepository;
import iseng.cafe.pos.services.MenuOrderDetailService;
import iseng.cafe.pos.services.MenuOrderService;
import iseng.cafe.pos.services.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuMenuOrderDetailServiceImpl extends CommonServiceImpl<MenuOrderDetail, Integer> implements MenuOrderDetailService {
    @Autowired
    private MenuOrderService menuOrderService;

    @Autowired
    private SnackService snackService;

    public MenuMenuOrderDetailServiceImpl(MenuOrderDetailRepository repository) {
        super(repository);
    }

}
