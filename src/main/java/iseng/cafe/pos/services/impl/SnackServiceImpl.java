package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.Snack;
import iseng.cafe.pos.repositories.SnackRepository;
import iseng.cafe.pos.services.SnackCategoryService;
import iseng.cafe.pos.services.SnackService;
import iseng.cafe.pos.services.SnackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnackServiceImpl extends CommonServiceImpl<Snack, Integer> implements SnackService {
    @Autowired
    private SnackCategoryService snackCategoryService;

    @Autowired
    private SnackTypeService snackTypeService;

    public SnackServiceImpl(SnackRepository repository) {
        super(repository);
    }

}
