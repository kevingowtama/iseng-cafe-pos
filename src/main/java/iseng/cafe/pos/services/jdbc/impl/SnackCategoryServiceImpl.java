package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.repositories.jdbc.SnackCategoryRepository;
import iseng.cafe.pos.services.jdbc.SnackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnackCategoryServiceImpl implements SnackCategoryService {
    @Autowired
    private SnackCategoryRepository snackCategoryRepository;

    @Override
    public SnackCategory save(SnackCategory entity) {
        return snackCategoryRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return snackCategoryRepository.remove(id);
    }

    @Override
    public SnackCategory findById(Integer id) {
        return snackCategoryRepository.findById(id);
    }

    @Override
    public List<SnackCategory> findAll() {
        return snackCategoryRepository.findAll();
    }
}
