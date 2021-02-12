package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.entities.Snack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackRepository extends JpaRepository<Snack, Integer> {
}
