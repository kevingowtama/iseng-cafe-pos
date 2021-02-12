package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.repositories.SnackTypeRepository;
import iseng.cafe.pos.services.SnackTypeService;
import org.springframework.stereotype.Service;

@Service
public class SnackTypeServiceImpl extends CommonServiceImpl<SnackType, Integer> implements SnackTypeService {
    public SnackTypeServiceImpl(SnackTypeRepository repository) {
        super(repository);
    }

}
