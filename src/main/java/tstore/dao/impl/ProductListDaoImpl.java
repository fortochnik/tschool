package tstore.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import tstore.dao.ProductListDao;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.enums.BasketOrderState;
import tstore.model.enums.PaymentStatus;
import tstore.utils.SessionAttributes;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by mipan on 11.10.2016.
 */
@Repository
public class ProductListDaoImpl extends GenericDaoImpl<ProductListEntity, Integer> implements ProductListDao {
    final static Logger logger = Logger.getLogger(ProductListDaoImpl.class);
    private ProductListEntity productInBasket;

    public ProductListEntity findProductInBasketById(ProductEntity productEntity, OrderEntity orderEntity) {
        String hql = "from ProductListEntity where product = :productEntity and consignment = :orderEntity";
        Query query = getCurrentSession().createQuery(hql).setParameter("productEntity", productEntity).setParameter("orderEntity", orderEntity);

        try {

            List list = query.list();
            productInBasket = (ProductListEntity) list.get(0);
            if (list.size() > 1) {
                throw new NonUniqueResultException(list.size());
            }
        } catch (IndexOutOfBoundsException e) {
            logger.info(MessageFormat.format("No result ProductListDaoImpl by : {0}  productEntity ", productEntity), e);
            return null;
        } catch (NonUniqueResultException e) {
            logger.info(MessageFormat.format("No Unique ProductListDaoImpl by : {0}  productEntity ", productEntity), e);
        }

        return productInBasket;
    }

    public List getTopTen() {
        String hql = "select product, count(product.count) as quantity from ProductListEntity as pr_list left join pr_list.product as product where pr_list.price is not null group by product.name order by quantity desc ";
        Query query = getCurrentSession().createQuery(hql).setMaxResults(10);
        List list = query.list();
        return list;
    }

    public BigDecimal getProceedsBy(int numberOfDaysToLookBack) {
        BigDecimal total = null;
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC+3"));//Moscow time
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -numberOfDaysToLookBack);//substract the number of days to look back
        Date dateToLookBackAfter = calendar.getTime();

        String hql = "select sum(pr.price * pr.count) as total from ProductListEntity as pr inner join pr.consignment as ord where ord.paymentStatus = :status and ord.orderDate >= :date";
//        Query query = getCurrentSession().createQuery(hql).setParameter("status", PaymentStatus.PAID).setParameter("date", dateToLookBackAfter, TemporalType.DATE);
        Query query = getCurrentSession().createQuery(hql).setParameter("status", PaymentStatus.PAID).setParameter("date", dateToLookBackAfter);
        try {
            List list = query.list();
            total = (BigDecimal) list.get(0);
            if (list.size() > 1) {
                throw new NonUniqueResultException(list.size());
            }
        } catch (IndexOutOfBoundsException e) {
            logger.info(MessageFormat.format("No result by : {0}  userId ", numberOfDaysToLookBack), e);
            return BigDecimal.ZERO;
        } catch (NonUniqueResultException e) {
            logger.error(MessageFormat.format("No Unique proceed Query by : {0}  userId ", numberOfDaysToLookBack), e);

        }
        if (total==null){
            total = BigDecimal.ZERO;
        }
        return total;
    }

    @Override
    public int getBasketProductCountByUserId(Integer userId) {
        String hql = "select sum (pr_list.count) from ProductListEntity as pr_list left join pr_list.consignment as consignment where consignment.client.id = :id and consignment.state = :status";
        Query query = getCurrentSession().createQuery(hql).setParameter("id", userId).setParameter("status", BasketOrderState.BASKET);
        List list = query.list();
        if (list != null && list.get(0)!=null ){
            return Integer.valueOf(list.get(0).toString());
        }
        return 0;
    }
}
