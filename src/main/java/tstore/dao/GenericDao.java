package tstore.dao;

import tstore.dao.transaction.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public interface GenericDao<T, Id extends Serializable> extends Transaction {

    void persist(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteAll(Class<T> clazz);

    T findById(Class<T> clazz, Id id);

    List<T> findAll(Class<T> clazz);


}
