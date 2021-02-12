package iseng.cafe.pos.repositories;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.entities.SnackCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackCategoryRepository extends JpaRepository<SnackCategory, Integer> {
}
