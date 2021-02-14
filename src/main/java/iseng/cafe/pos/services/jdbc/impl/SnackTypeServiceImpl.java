package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.repositories.jdbc.SnackTypeRepository;
import iseng.cafe.pos.services.jdbc.SnackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnackTypeServiceImpl implements SnackTypeService {
    @Autowired
    private SnackTypeRepository snackTypeRepository;

    @Override
    public SnackType save(SnackType entity) {
        return snackTypeRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return snackTypeRepository.remove(id);
    }

    @Override
    public SnackType findById(Integer id) {
        return snackTypeRepository.findById(id);
    }

    @Override
    public List<SnackType> findAll() {
        return snackTypeRepository.findAll();
    }
}
