package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.PaymentMethod;
import iseng.cafe.pos.repositories.jdbc.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PaymentMethod save(PaymentMethod entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO payment_method(name, created_at, updated_at) values (?, ?, ?)";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE payment_method SET name = ?, updated_at = ? WHERE id = ?";
            jdbcTemplate.update(query, entity.getName(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM payment_method WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public PaymentMethod findById(Integer id) throws DataAccessException {
        List<PaymentMethod> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM payment_method where id=?", (rs, i) -> {
            PaymentMethod paymentMethod = new PaymentMethod();

            paymentMethod.setId(rs.getInt("id"));
            paymentMethod.setName(rs.getString("name"));
            paymentMethod.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            paymentMethod.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return paymentMethod;
        }, id);
        return list.get(0);
    }

    @Override
    public List<PaymentMethod> findAll() throws DataAccessException {
        List<PaymentMethod> list = jdbcTemplate.query("SELECT id, name, created_at, updated_at FROM payment_method", (rs, i) -> {
            PaymentMethod paymentMethod = new PaymentMethod();

            paymentMethod.setId(rs.getInt("id"));
            paymentMethod.setName(rs.getString("name"));
            paymentMethod.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            paymentMethod.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return paymentMethod;
        });
        return list;
    }

}
