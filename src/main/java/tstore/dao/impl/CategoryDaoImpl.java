package tstore.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import tstore.dao.CategoryDao;
import tstore.model.CategoryEntity;

/**
 * Created by mipan on 02.10.2016.
 */
@Repository
public class CategoryDaoImpl extends GenericDaoImpl<CategoryEntity,Integer> implements CategoryDao {


    public void deleteById(int id) {
        String hql = "delete CategoryEntity where id = 3";
        Query query = getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

}
