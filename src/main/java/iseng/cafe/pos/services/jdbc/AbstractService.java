package iseng.cafe.pos.services.jdbc;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface AbstractService<T, ID>{
    T save(T entity) throws DataAccessException;

    Boolean remove(ID id) throws DataAccessException;

    T findById(ID id) throws DataAccessException;

    List<T> findAll() throws DataAccessException;
}
