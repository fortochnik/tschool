package tstore.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
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
import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public class OrderDaoImpl extends GenericDaoImpl<OrderEntity, Integer> implements OrderDao {

    public OrderEntity findBasketByUserId(Integer userId) {
        OrderEntity basket = null;

        String hql = "from OrderEntity where client.id = :userId and state = :basketState";
        Query query = getCurrentSession().createQuery(hql).setParameter("userId", userId).setParameter("basketState", BasketOrderState.BASKET);
        try {
            basket = (OrderEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
//            todo Change exception (?)
//            todo add logging
        }

        return basket;
    }

    public List<OrderEntity> findOrdersByUser(UserEntity userEntity) {

        String hql = "from OrderEntity where client = :user order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("user", userEntity);
        List list = query.list();
        return list;

//        Hibernate.initialize(userEntity.getOrders());
//        Set<OrderEntity> orders = userEntity.getOrders();
//        for (OrderEntity order : orders) {
//            Hibernate.initialize(order.getProductList());
//        }
//
//        return orders;

    }

    public List<OrderEntity> find(String orderNumber, String userEmail) {

        int orderId = Integer.parseInt(orderNumber);
        Query query = null;
        if (orderNumber != "" && userEmail != "") {
            String hql = "from OrderEntity where client.email = :email and id = :orderNumber order by id desc";
            query = getCurrentSession().createQuery(hql).setParameter("email", userEmail).setParameter("orderNumber", orderId);
        }
        if (orderNumber == "" && userEmail != null) {
            String hql = "from OrderEntity where client.email = :email order by id desc";
            query = getCurrentSession().createQuery(hql).setParameter("email", userEmail);
        }
        if (orderNumber != null && userEmail == "") {
            String hql = "from OrderEntity where id = :orderNumber order by id desc";
            query = getCurrentSession().createQuery(hql).setParameter("orderNumber", orderId);
        }

        List list = query.list();
        return list;
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

    public List<OrderEntity> findAllOrders() {
        String hql = "from OrderEntity where state = :state order by id desc";
        Query query = getCurrentSession().createQuery(hql).setParameter("state", BasketOrderState.ORDER);

        return query.list();
    }
}
