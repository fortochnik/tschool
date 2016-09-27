package tstore.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public interface CruGenericDao<T, Id extends Serializable> {

    void persist(T entity);

    void update(T entity);

    T findById(Id id);

    List<T> findAll();
}
