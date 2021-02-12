package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<MenuOrder, Integer> {
}
