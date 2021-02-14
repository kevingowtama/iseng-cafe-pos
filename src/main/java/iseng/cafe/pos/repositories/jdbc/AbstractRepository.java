package iseng.cafe.pos.repositories.jdbc;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface AbstractRepository <T, ID>{
    T save(T entity) throws DataAccessException;

    Boolean remove(ID id) throws DataAccessException;

    T findById(ID id) throws DataAccessException;

    List<T> findAll() throws DataAccessException;
}
