package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.MenuOrderDetail;
import iseng.cafe.pos.repositories.jdbc.MenuOrderDetailRepository;
import iseng.cafe.pos.services.jdbc.MenuOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuOrderDetailServiceImpl implements MenuOrderDetailService {
    @Autowired
    private MenuOrderDetailRepository menuOrderDetailRepository;

    @Override
    public MenuOrderDetail save(MenuOrderDetail entity) {
        return menuOrderDetailRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return menuOrderDetailRepository.remove(id);
    }

    @Override
    public MenuOrderDetail findById(Integer id) {
        return menuOrderDetailRepository.findById(id);
    }

    @Override
    public List<MenuOrderDetail> findAll() {
        return menuOrderDetailRepository.findAll();
    }
}
