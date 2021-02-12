package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
