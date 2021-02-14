package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.repositories.jdbc.SnackCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SnackCategoryRepositoryImpl implements SnackCategoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SnackCategory save(SnackCategory entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO snack_category(name, created_at, updated_at) values (?, ?, ?)";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE snack_category SET name = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM snack_category WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public SnackCategory findById(Integer id) throws DataAccessException {
        List<SnackCategory> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM snack_category where id=?", (rs, i) -> {
            SnackCategory snackCategory = new SnackCategory();

            snackCategory.setId(rs.getInt("id"));
            snackCategory.setName(rs.getString("name"));
            snackCategory.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            snackCategory.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return snackCategory;
        }, id);
        return list.get(0);
    }

    @Override
    public List<SnackCategory> findAll() throws DataAccessException {
        List<SnackCategory> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM snack_category", (rs, i) -> {
            SnackCategory snackCategory = new SnackCategory();

            snackCategory.setId(rs.getInt("id"));
            snackCategory.setName(rs.getString("name"));
            snackCategory.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            snackCategory.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return snackCategory;
        });
        return list;
    }

    private List<SnackCategory> getList(String query){
        List<SnackCategory> list = jdbcTemplate.query("SELECT id, name FROM ", (rs, i) -> {
            SnackCategory snackCategory = new SnackCategory();

            snackCategory.setId(rs.getInt("region_id"));
            snackCategory.setName(rs.getString("name"));
            return snackCategory;
        });
        return list;
    }
}
