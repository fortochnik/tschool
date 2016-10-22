package tstore.dao.impl;

import org.hibernate.query.Query;
import tstore.dao.CategoryDao;
import tstore.model.CategoryEntity;

/**
 * Created by mipan on 02.10.2016.
 */
public class CategoryDaoImpl extends GenericDaoImpl<CategoryEntity,Integer> implements CategoryDao {


    public void deleteById(int id) {
//        String hql = "delete CategoryEntity where id = :id";
//        Query query = getCurrentSession().createQuery(hql).setParameter("id", id);
        String hql = "delete CategoryEntity where id = 3";
        Query query = getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

}
