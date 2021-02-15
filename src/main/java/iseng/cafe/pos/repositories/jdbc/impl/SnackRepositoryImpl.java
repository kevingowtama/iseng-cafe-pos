package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.Snack;
import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.entities.SnackType;
import iseng.cafe.pos.repositories.jdbc.SnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SnackRepositoryImpl implements SnackRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Snack save(Snack entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO snack(snack_type_id, snack_category_id, name, price, description, created_at, updated_at) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, entity.getSnackType().getId(), entity.getSnackCategory().getId(), entity.getName(),
                    entity.getPrice(), entity.getDescription(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE snack SET snack_type_id = ?, snack_category_id = ?, name = ?, price = ?, description = ?, updated_at = ?" +
                    "WHERE id = ?";
            jdbcTemplate.update(query, entity.getSnackType().getId(), entity.getSnackCategory().getId(), entity.getName(),
                    entity.getPrice(), entity.getDescription(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM snack WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public Snack findById(Integer id) throws DataAccessException {
        List<Snack> list = jdbcTemplate.query(
                "SELECT snack.id, snack.name, snack.price, snack.description, snack_type.name, snack_category.name, " +
                        "snack.created_at, snack.updated_at FROM snack " +
                        "JOIN snack_type ON snack.snack_type_id = snack_type.id " +
                        "JOIN snack_category ON snack.snack_category_id = snack_category.id " +
                        "WHERE snack.id=?", (rs, i) -> {
                    Snack snack = new Snack();
                    SnackType snackType = new SnackType();
                    SnackCategory snackCategory = new SnackCategory();

                    snack.setId(rs.getInt("snack.id"));
                    snack.setName(rs.getString("snack.name"));
                    snack.setPrice(rs.getDouble("snack.price"));
                    snack.setDescription(rs.getString("snack.description"));
                    snack.setCreatedAt(rs.getDate("created_at")
                            .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
                    snack.setUpdatedAt(rs.getDate("updated_at")
                            .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

                    snackCategory.setName(rs.getString("name"));
                    snack.setSnackCategory(snackCategory);

                    snackType.setName(rs.getString("name"));
                    snack.setSnackType(snackType);

                    return snack;
                }, id);
        return list.get(0);
    }

    @Override
    public List<Snack> findAll() throws DataAccessException {
        List<Snack> list = jdbcTemplate.query(
            "SELECT snack.id, snack.name, snack.price, snack.description, snack_type.name, snack_category.name, " +
                    "snack.created_at, snack.updated_at FROM snack " +
                    "JOIN snack_type ON snack.snack_type_id = snack_type.id " +
                    "JOIN snack_category ON snack.snack_category_id = snack_category.id", (rs, i) -> {
                Snack snack = new Snack();
                SnackType snackType = new SnackType();
                SnackCategory snackCategory = new SnackCategory();

                snack.setId(rs.getInt("snack.id"));
                snack.setName(rs.getString("snack.name"));
                snack.setPrice(rs.getDouble("snack.price"));
                snack.setDescription(rs.getString("snack.description"));
                snack.setCreatedAt(rs.getDate("created_at")
                        .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
                snack.setUpdatedAt(rs.getDate("updated_at")
                        .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

                snackCategory.setName(rs.getString("name"));
                snack.setSnackCategory(snackCategory);

                snackType.setName(rs.getString("name"));
                snack.setSnackType(snackType);

                return snack;
        });
        return list;
    }
}
