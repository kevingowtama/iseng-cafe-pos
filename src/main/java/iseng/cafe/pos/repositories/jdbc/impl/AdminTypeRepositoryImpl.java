package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.models.adminType.AdminTypeRequest;
import iseng.cafe.pos.repositories.jdbc.AdminTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdminTypeRepositoryImpl implements AdminTypeRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AdminType save(AdminType entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO admin_type(name, created_at, updated_at) values (?, ?, ?)";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE admin_type SET name = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM admin_type WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public AdminType findById(Integer id) throws DataAccessException {
        List<AdminType> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM admin_type where id=?", (rs, i) -> {
            AdminType adminType = new AdminType();

            adminType.setId(rs.getInt("id"));
            adminType.setName(rs.getString("name"));
            adminType.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            adminType.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return adminType;
        }, id);
        return list.get(0);
    }

    @Override
    public List<AdminType> findAll() throws DataAccessException {
        List<AdminType> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM admin_type", (rs, i) -> {
            AdminType adminType = new AdminType();

            adminType.setId(rs.getInt("id"));
            adminType.setName(rs.getString("name"));
            adminType.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            adminType.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return adminType;
        });
        return list;
    }

    private List<AdminType> getList(String query){
        List<AdminType> list = jdbcTemplate.query("SELECT id, name FROM ", (rs, i) -> {
            AdminType adminType = new AdminType();

            adminType.setId(rs.getInt("region_id"));
            adminType.setName(rs.getString("name"));
            return adminType;
        });
        return list;
    }
}
