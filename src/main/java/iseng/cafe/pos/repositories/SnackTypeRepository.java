package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.entities.SnackType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackTypeRepository extends JpaRepository<SnackType, Integer> {
}
