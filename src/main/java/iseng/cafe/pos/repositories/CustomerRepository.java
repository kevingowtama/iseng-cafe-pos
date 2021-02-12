package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
