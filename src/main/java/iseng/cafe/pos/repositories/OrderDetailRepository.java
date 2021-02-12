package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.MenuOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<MenuOrderDetail, Integer> {
}
