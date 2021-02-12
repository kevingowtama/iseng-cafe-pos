package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.repositories.AdminRepository;
import iseng.cafe.pos.services.AdminService;
import iseng.cafe.pos.services.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl extends CommonServiceImpl<Admin, Integer> implements AdminService {
    @Autowired
    private AdminTypeService adminTypeService;

    public AdminServiceImpl(AdminRepository repository) {
        super(repository);
    }
}
