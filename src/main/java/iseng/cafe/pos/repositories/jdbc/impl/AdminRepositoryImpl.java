package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.repositories.jdbc.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdminRepositoryImpl implements AdminRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Admin save(Admin entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO admin(email, name, username, password, admin_type_id, created_at, updated_at) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, entity.getEmail(), entity.getName(), entity.getUsername()
                    , entity.getPassword(), entity.getAdminType().getId(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE admin SET name = ?, email = ?, username = ?, password = ?, admin_type_id = ?, updated_at = ?" +
                    "WHERE id = ?";
            jdbcTemplate.update(query, entity.getId(), entity.getEmail(),
                    entity.getUsername(), entity.getPassword(), entity.getAdminType().getId(),
                    LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM admin WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public Admin findById(Integer id) throws DataAccessException {
        List<Admin> list = jdbcTemplate.query(
                "SELECT admin.id, admin.email, admin.username, admin_type.id, admin_type.name, admin.created_at, admin.updated_at " +
                        "FROM admin JOIN admin_type ON admin.admin_type_id = admin_type.id where admin.id=?", (rs, i) -> {
            Admin admin = new Admin();
            AdminType adminType = new AdminType();

            admin.setId(rs.getInt("admin.id"));
            admin.setName(rs.getString("admin.email"));
            admin.setEmail(rs.getString("admin.email"));
            admin.setUsername(rs.getString("admin.username"));
            admin.setCreatedAt(rs.getDate("admin.created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            admin.setUpdatedAt(rs.getDate("admin.updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            adminType.setId(rs.getInt("admin_type.id"));
            adminType.setName(rs.getString("name"));
            admin.setAdminType(adminType);

            return admin;
        }, id);
        return list.get(0);
    }

    @Override
    public List<Admin> findAll() throws DataAccessException {
        List<Admin> list = jdbcTemplate.query("SELECT admin.id, admin.email, admin.username, admin_type.id, admin_type.name, " +
                "admin.created_at, admin.updated_at FROM admin JOIN admin_type ON admin.admin_type_id = admin_type.id", (rs, i) -> {
            Admin admin = new Admin();
            AdminType adminType = new AdminType();

            admin.setId(rs.getInt("admin.id"));
            admin.setName(rs.getString("admin.email"));
            admin.setEmail(rs.getString("admin.email"));
            admin.setUsername(rs.getString("admin.username"));
            admin.setCreatedAt(rs.getDate("admin.created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            admin.setUpdatedAt(rs.getDate("admin.updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            adminType.setId(rs.getInt("admin_type.id"));
            adminType.setName(rs.getString("name"));
            admin.setAdminType(adminType);

            return admin;
        });
        return list;
    }
}
