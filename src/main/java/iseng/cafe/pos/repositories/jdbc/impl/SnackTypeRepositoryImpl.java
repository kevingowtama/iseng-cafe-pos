package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.repositories.jdbc.SnackTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SnackTypeRepositoryImpl implements SnackTypeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SnackType save(SnackType entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO snack_type(name, created_at, updated_at) values (?, ?, ?)";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE snack_type SET name = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM snack_type WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public SnackType findById(Integer id) throws DataAccessException {
        List<SnackType> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM snack_type where id=?", (rs, i) -> {
            SnackType snackType = new SnackType();

            snackType.setId(rs.getInt("id"));
            snackType.setName(rs.getString("name"));
            snackType.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            snackType.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return snackType;
        }, id);
        return list.get(0);
    }

    @Override
    public List<SnackType> findAll() throws DataAccessException {
        List<SnackType> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM snack_type", (rs, i) -> {
            SnackType snackType = new SnackType();

            snackType.setId(rs.getInt("id"));
            snackType.setName(rs.getString("name"));
            snackType.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            snackType.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return snackType;
        });
        return list;
    }

    private List<SnackType> getList(String query){
        List<SnackType> list = jdbcTemplate.query("SELECT id, name FROM ", (rs, i) -> {
            SnackType snackType = new SnackType();

            snackType.setId(rs.getInt("region_id"));
            snackType.setName(rs.getString("name"));
            return snackType;
        });
        return list;
    }
}
