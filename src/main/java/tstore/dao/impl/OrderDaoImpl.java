package tstore.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import tstore.dao.OrderDao;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;
import tstore.model.enums.OrderStatus;
import tstore.model.enums.PaymentStatus;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
@Repository
public class OrderDaoImpl extends GenericDaoImpl<OrderEntity, Integer> implements OrderDao {
    final static Logger logger = Logger.getLogger(OrderDaoImpl.class);
    public OrderEntity findBasketByUserId(Integer userId) {
        OrderEntity basket = null;

        String hql = "from OrderEntity where client.id = :userId and state = :basketState";
        Query query = getCurrentSession().createQuery(hql).setParameter("userId", userId).setParameter("basketState", BasketOrderState.BASKET);
        try {
            List list = query.list();
            basket = (OrderEntity) list.get(0);
            if (list.size()>1){
                throw new NonUniqueResultException(list.size());
            }
        } catch (IndexOutOfBoundsException e) {
            logger.info(MessageFormat.format("No result OrderEntity by : {0}  userId ", userId), e);
            return null;
        } catch (NonUniqueResultException e) {
            logger.error(MessageFormat.format("No Unique OrderEntity by : {0}  userId ", userId), e);

        }

        return basket;
    }

    public List<OrderEntity> findOrdersByUser(UserEntity userEntity) {

        String hql = "from OrderEntity where client = :user order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("user", userEntity);
        List list = query.list();
        return list;

    }

    public List<OrderEntity> find(String orderNumber, String userEmail) {


        Query query = null;
        if (!orderNumber.equals("") && !userEmail.equals("")) {
            int orderId = Integer.parseInt(orderNumber);
            String hql = "from OrderEntity where client.email = :email and id = :orderNumber  and orderStatus != :draft order by id desc";
            query = getCurrentSession().createQuery(hql).setParameter("email", userEmail).setParameter("orderNumber", orderId).setParameter("draft", OrderStatus.DRAFT);
        }
        if (orderNumber.equals("") && !userEmail.equals("")) {
            String hql = "from OrderEntity where client.email = :email and orderStatus != :draft  order by id desc";
            query = getCurrentSession().createQuery(hql).setParameter("email", userEmail).setParameter("draft", OrderStatus.DRAFT);
        }
        if (!orderNumber.equals("") && userEmail.equals("")) {
            int orderId = Integer.parseInt(orderNumber);
            String hql = "from OrderEntity where id = :orderNumber  and orderStatus != :draft  order by id desc";
            query = getCurrentSession().createQuery(hql).setParameter("orderNumber", orderId).setParameter("draft", OrderStatus.DRAFT);
        }

        if (query!=null) {
            List list = query.list();
            return list;
        }
        else {
            return null;
        }
    }

    public List<OrderEntity> findNotDelivered() {
        String hql = "from OrderEntity where orderStatus != :orderStatusdelivery and orderStatus != :orderStatusReceived and orderStatus != :draft  order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("orderStatusdelivery", OrderStatus.DELIVERY).setParameter("draft", OrderStatus.DRAFT)
                .setParameter("orderStatusReceived", OrderStatus.RECEIVED);

        return query.list();
    }

    public List<OrderEntity> findPaid() {
        String hql = "from OrderEntity where paymentStatus != :paymentStatus order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("paymentStatus", PaymentStatus.NOT_PAID);

        return query.list();
    }
    public List<OrderEntity> findNotPaid() {
        String hql = "from OrderEntity where paymentStatus != :paymentStatus order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("paymentStatus", PaymentStatus.PAID);

        return query.list();
    }

    public List<OrderEntity> findAllOrders() {
        String hql = "from OrderEntity where state = :state order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("state", BasketOrderState.ORDER);

        return query.list();
    }
}
