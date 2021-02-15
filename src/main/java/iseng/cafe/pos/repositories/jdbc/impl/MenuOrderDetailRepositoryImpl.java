package iseng.cafe.pos.repositories.jdbc.impl;

import iseng.cafe.pos.entities.*;
import iseng.cafe.pos.repositories.jdbc.MenuOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MenuOrderDetailRepositoryImpl implements MenuOrderDetailRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MenuOrderDetail save(MenuOrderDetail entity) throws DataAccessException {
        if(entity.getId() == null){
            String query = "INSERT INTO menu_order_detail(menu_order_id, snack_id, quantity, sub_total, notes, created_at, updated_at) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, entity.getMenuOrder().getId(), entity.getSnack().getId(), entity.getQuantity(),
                     entity.getSubTotal(), entity.getNotes(), LocalDateTime.now(), LocalDateTime.now());
        }
        else{
            String query = "UPDATE menu_order_detail SET snack_id = ?, menu_order_id = ?, quantity = ?, sub_total = ?, notes = ?, updated_at = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(query, entity.getSnack().getId(), entity.getMenuOrder().getId(), entity.getQuantity(),
                    entity.getSubTotal(), entity.getNotes(), LocalDateTime.now(), entity.getId());
        }
        return entity;
    }

    @Override
    public Boolean remove(Integer id) throws DataAccessException {
        String query = "DELETE FROM menu_order_detail WHERE id=?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

    @Override
    public MenuOrderDetail findById(Integer id) throws DataAccessException {
        List<MenuOrderDetail> list = jdbcTemplate.query(
            "SELECT menu_order_detail.id, menu_order_detail.quantity, menu_order_detail.sub_total, menu_order_detail.created_at, " +
                    "menu_order_detail.updated_at, menu_order_detail.notes, snack.name, snack.price, menu_order.id FROM menu_order_detail " +
                    "JOIN menu_order ON menu_order_detail.menu_order_id = menu_order.id " +
                    "Join snack ON menu_order_detail.snack_id = snack.id " +
                    "WHERE menu_order_detail.id=?", (rs, i) -> {
                MenuOrderDetail menuOrderDetail = new MenuOrderDetail();
                MenuOrder menuOrder = new MenuOrder();
                Snack snack = new Snack();

                menuOrderDetail.setId(rs.getInt("menu_order_detail.id"));
                menuOrderDetail.setQuantity(rs.getInt("menu_order_detail.quantity"));
                menuOrderDetail.setSubTotal(rs.getDouble("menu_order_detail.sub_total"));
                menuOrderDetail.setNotes(rs.getString("menu_order_detail.notes"));
                menuOrderDetail.setCreatedAt(rs.getDate("menu_order_detail.created_at")
                        .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
                menuOrderDetail.setUpdatedAt(rs.getDate("menu_order_detail.updated_at")
                        .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

                snack.setName(rs.getString("snack.name"));
                snack.setPrice(rs.getDouble("snack.price"));
                menuOrderDetail.setSnack(snack);

                menuOrder.setId(rs.getInt("menu_order.id"));
                menuOrderDetail.setMenuOrder(menuOrder);

                return menuOrderDetail;
            }, id);
        return list.get(0);
    }

    @Override
    public List<MenuOrderDetail> findAll() throws DataAccessException {
        List<MenuOrderDetail> list = jdbcTemplate.query(
            "SELECT menu_order_detail.id, menu_order_detail.quantity, menu_order_detail.sub_total, menu_order_detail.created_at, " +
                    "menu_order_detail.updated_at, menu_order_detail.notes, snack.name, snack.price, menu_order.id FROM menu_order_detail " +
                    "JOIN menu_order ON menu_order_detail.menu_order_id = menu_order.id " +
                    "Join snack ON menu_order_detail.snack_id = snack.id", (rs, i) -> {
                MenuOrderDetail menuOrderDetail = new MenuOrderDetail();
                MenuOrder menuOrder = new MenuOrder();
                Snack snack = new Snack();

                menuOrderDetail.setId(rs.getInt("menu_order_detail.id"));
                menuOrderDetail.setQuantity(rs.getInt("menu_order_detail.quantity"));
                menuOrderDetail.setSubTotal(rs.getDouble("menu_order_detail.sub_total"));
                menuOrderDetail.setNotes(rs.getString("menu_order_detail.notes"));
                menuOrderDetail.setCreatedAt(rs.getDate("menu_order_detail.created_at")
                        .toLocalDate().atTime(rs.getTime("created_at").toLocalTime()));
                menuOrderDetail.setUpdatedAt(rs.getDate("menu_order_detail.updated_at")
                        .toLocalDate().atTime(rs.getTime("updated_at").toLocalTime()));

                snack.setName(rs.getString("snack.name"));
                snack.setPrice(rs.getDouble("snack.price"));
                menuOrderDetail.setSnack(snack);

                menuOrder.setId(rs.getInt("menu_order.id"));
                menuOrderDetail.setMenuOrder(menuOrder);

                return menuOrderDetail;
        });
        return list;
    }
}
