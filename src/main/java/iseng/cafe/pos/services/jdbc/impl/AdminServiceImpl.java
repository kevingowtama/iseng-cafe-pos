package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.repositories.jdbc.AdminRepository;
import iseng.cafe.pos.services.jdbc.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin save(Admin entity) {
        return adminRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return adminRepository.remove(id);
    }

    @Override
    public Admin findById(Integer id) {
        return adminRepository.findById(id);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }
}
