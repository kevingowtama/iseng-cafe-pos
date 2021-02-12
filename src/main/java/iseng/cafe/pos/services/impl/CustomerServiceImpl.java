package iseng.cafe.pos.services.impl;

import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.repositories.CustomerRepository;
import iseng.cafe.pos.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends CommonServiceImpl<Customer, Integer> implements CustomerService {

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }

}
