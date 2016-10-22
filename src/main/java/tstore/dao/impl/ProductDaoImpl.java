package tstore.dao.impl;

import org.hibernate.query.Query;
import tstore.dao.ProductDao;
import tstore.model.ProductEntity;
import tstore.model.enums.BasketOrderState;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public class ProductDaoImpl extends GenericDaoImpl<ProductEntity, Integer> implements ProductDao {

    public List<ProductEntity> getByCategoryId(int categoryId) {
        String hql = "from ProductEntity where category.id = :categoryId";
        TypedQuery<ProductEntity> query = getCurrentSession().createQuery(hql, ProductEntity.class).setParameter("categoryId", categoryId);
        return query.getResultList();
    }
}
