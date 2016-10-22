package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.ProductListDao;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.enums.PaymentStatus;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;

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

    public List getTopTen() {
        String hql = "select product, count(product.count) as quantity from ProductListEntity as pr_list left join pr_list.product as product where pr_list.price is not null group by product.name order by quantity desc ";
//        Query query = getCurrentSession().createQuery(hql).setMaxResults(10);
        Query query = getCurrentSession().createQuery(hql).setMaxResults(10);
        List list = query.getResultList();
        return list;
    }

    public BigDecimal getProceedsBy(int numberOfDaysToLookBack) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC+3"));//Moscow time
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -numberOfDaysToLookBack);//substract the number of days to look back
        Date dateToLookBackAfter = calendar.getTime();

        String hql = "select sum(pr.price) as total from ProductListEntity as pr inner join pr.consignment as ord where ord.paymentStatus = :status and ord.orderDate >= :date";
        TypedQuery<BigDecimal> query = getCurrentSession().createQuery(hql, BigDecimal.class).setParameter("status", PaymentStatus.PAID).setParameter("date", dateToLookBackAfter, TemporalType.DATE);
        BigDecimal total = query.getSingleResult();

        return total;
    }
}
