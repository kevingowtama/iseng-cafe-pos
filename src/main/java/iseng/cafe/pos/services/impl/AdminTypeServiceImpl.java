package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.repositories.AdminTypeRepository;
import iseng.cafe.pos.services.AdminTypeService;
import org.springframework.stereotype.Service;

@Service
public class AdminTypeServiceImpl extends CommonServiceImpl<AdminType, Integer> implements AdminTypeService {

    public AdminTypeServiceImpl(AdminTypeRepository repository) {
        super(repository);
    }
}