package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.MenuOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOrderDetailRepository extends JpaRepository<MenuOrderDetail, Integer> {
}
