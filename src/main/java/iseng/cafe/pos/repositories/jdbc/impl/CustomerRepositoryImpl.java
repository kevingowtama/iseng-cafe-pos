package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.repositories.jdbc.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Customer save(Customer entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO customer(first_name, last_name, email, phone, created_at, updated_at) " +
                    "values (?,?,?,?,?,?)";
            jdbcTemplate.update(query, entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhone(),
            LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, phone = ?, updated_at = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(query, entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhone(),
                    LocalDateTime.now(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM customer WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public Customer findById(Integer id) throws DataAccessException {
        List<Customer> list = jdbcTemplate.query("SELECT id, first_name, last_name, email, phone, created_at, updated_at " +
                "FROM customer where id=?", (rs, i) -> {
            Customer customer = new Customer();

            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            customer.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            customer.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return customer;
        }, id);
        return list.get(0);
    }

    @Override
    public List<Customer> findAll() throws DataAccessException {
        List<Customer> list = jdbcTemplate.query("SELECT id, first_name, last_name, email, phone, created_at, updated_at " +
                "FROM customer", (rs, i) -> {
            Customer customer = new Customer();

            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            customer.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            customer.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            return customer;
        });
        return list;
    }
}
