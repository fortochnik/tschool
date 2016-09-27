package tstore.dao;

import java.io.Serializable;

/**
 * Created by mipan on 28.09.2016.
 */
public interface CrudGenericDao<T, Id extends Serializable> extends CruGenericDao {

    void delete(T entity);

    void deleteAll();

}
