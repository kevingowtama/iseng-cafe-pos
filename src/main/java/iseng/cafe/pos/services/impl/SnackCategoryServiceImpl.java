package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.repositories.SnackCategoryRepository;
import iseng.cafe.pos.services.SnackCategoryService;
import org.springframework.stereotype.Service;

@Service
public class SnackCategoryServiceImpl extends CommonServiceImpl<SnackCategory, Integer> implements SnackCategoryService {
    public SnackCategoryServiceImpl(SnackCategoryRepository repository) {
        super(repository);
    }

}
