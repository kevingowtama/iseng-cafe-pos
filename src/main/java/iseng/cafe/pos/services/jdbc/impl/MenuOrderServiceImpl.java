package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.repositories.jdbc.MenuOrderRepository;
import iseng.cafe.pos.services.jdbc.MenuOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuOrderServiceImpl implements MenuOrderService {
    @Autowired
    private MenuOrderRepository menuOrderRepository;

    @Override
    public MenuOrder save(MenuOrder entity) {
        return menuOrderRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return menuOrderRepository.remove(id);
    }

    @Override
    public MenuOrder findById(Integer id) {
        return menuOrderRepository.findById(id);
    }

    @Override
    public List<MenuOrder> findAll() {
        return menuOrderRepository.findAll();
    }
}
