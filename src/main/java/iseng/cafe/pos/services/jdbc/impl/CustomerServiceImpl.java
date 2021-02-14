package iseng.cafe.pos.services.jdbc.impl;

import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.repositories.jdbc.CustomerRepository;
import iseng.cafe.pos.services.jdbc.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public Boolean remove(Integer id) {
        return customerRepository.remove(id);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
