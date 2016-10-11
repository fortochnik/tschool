package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.ProductListDao;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;

import javax.persistence.NoResultException;

/**
 * Created by mipan on 11.10.2016.
 */
public class ProductListDaoImpl extends GenericDaoImpl<ProductListEntity, Integer> implements ProductListDao {
    private ProductListEntity productInBasket;
    public ProductListEntity findProductInBasketById(ProductEntity productEntity, OrderEntity orderEntity) {
        String hql = "from ProductListEntity where product = :productEntity and consignment = :orderEntity";
        Query query = getCurrentSession().createQuery(hql).setParameter("productEntity", productEntity).setParameter("orderEntity", orderEntity);

        try {
            productInBasket = (ProductListEntity) query.getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
        catch (NonUniqueResultException e)
        {
//            todo Change exception (?)
//            todo add logging
        }

        return productInBasket;
    }
}
