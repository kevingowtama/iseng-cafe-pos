package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.repositories.jdbc.AdminTypeRepository;
import iseng.cafe.pos.services.jdbc.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTypeServiceImpl implements AdminTypeService{
    @Autowired
    private AdminTypeRepository adminTypeRepository;

    @Override
    public AdminType save(AdminType entity) {
        return adminTypeRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return adminTypeRepository.remove(id);
    }

    @Override
    public AdminType findById(Integer id) {
        return adminTypeRepository.findById(id);
    }

    @Override
    public List<AdminType> findAll() {
        return adminTypeRepository.findAll();
    }
}
