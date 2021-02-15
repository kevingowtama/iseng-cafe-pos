package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.entities.MenuOrder;
import iseng.cafe.pos.repositories.jdbc.MenuOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MenuOrderRepositoryImpl implements MenuOrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MenuOrder save(MenuOrder entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO menu_order(total_cost, description, customer_id, admin_id, created_at, updated_at) " +
                    "values (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, entity.getTotalCost(), entity.getDescription(), entity.getCustomer().getId()
                    , entity.getAdmin().getId(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE menu_order SET total_cost = ?, description = ?, customer_id = ?, admin_id = ?, updated_at = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(query, entity.getTotalCost(), entity.getDescription(), entity.getCustomer().getId(),
                    entity.getAdmin().getId(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM menu_order WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public MenuOrder findById(Integer id) throws DataAccessException {
        List<MenuOrder> list = jdbcTemplate.query(
                "SELECT menu_order.id, menu_order.total_cost, menu_order.description, menu_order.created_at, menu_order.updated_at, " +
                        "admin.name, admin.email, customer.first_name, customer.last_name, customer.email FROM menu_order " +
                        "JOIN customer ON menu_order.customer_id = customer.id " +
                        "JOIN admin ON menu_order.admin_id = admin.id " +
                        "WHERE menu_order.id = ?", (rs, i) -> {
                    MenuOrder menuOrder = new MenuOrder();
                    Customer Customer = new Customer();
                    Admin admin = new Admin();

                    menuOrder.setId(rs.getInt("menu_order.id"));
                    menuOrder.setTotalCost(rs.getDouble("menu_order.total_cost"));
                    menuOrder.setDescription(rs.getString("menu_order.description"));
                    menuOrder.setCreatedAt(rs.getDate("created_at")
                            .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
                    menuOrder.setUpdatedAt(rs.getDate("updated_at")
                            .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

                    admin.setName(rs.getString("admin.name"));
                    admin.setEmail(rs.getString("admin.email"));
                    menuOrder.setAdmin(admin);

                    Customer.setFirstName(rs.getString("customer.first_name"));
                    Customer.setLastName(rs.getString("customer.last_name"));
                    Customer.setEmail(rs.getString("customer.email"));
                    menuOrder.setCustomer(Customer);

                    return menuOrder;
                }, id);
        return list.get(0);
    }

    @Override
    public List<MenuOrder> findAll() throws DataAccessException {
        List<MenuOrder> list = jdbcTemplate.query("SELECT menu_order.id, menu_order.total_cost, menu_order.description, menu_order.created_at, " +
                "menu_order.updated_at, admin.name, admin.email, customer.first_name, customer.last_name, customer.email FROM menu_order " +
                "JOIN customer ON menu_order.customer_id = customer.id " +
                "JOIN admin ON menu_order.admin_id = admin.id ", (rs, i) -> {
            MenuOrder menuOrder = new MenuOrder();
            Customer Customer = new Customer();
            Admin admin = new Admin();

            menuOrder.setId(rs.getInt("menu_order.id"));
            menuOrder.setTotalCost(rs.getDouble("menu_order.total_cost"));
            menuOrder.setDescription(rs.getString("menu_order.description"));
            menuOrder.setCreatedAt(rs.getDate("created_at")
                    .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
            menuOrder.setUpdatedAt(rs.getDate("updated_at")
                    .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

            admin.setName(rs.getString("admin.name"));
            admin.setEmail(rs.getString("admin.email"));
            menuOrder.setAdmin(admin);

            Customer.setFirstName(rs.getString("customer.first_name"));
            Customer.setLastName(rs.getString("customer.last_name"));
            Customer.setEmail(rs.getString("customer.email"));
            menuOrder.setCustomer(Customer);

            return menuOrder;
        });
        return list;
    }
}
