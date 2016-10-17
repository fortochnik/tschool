package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.OrderDao;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;

import javax.persistence.NoResultException;
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
        }
        catch(NoResultException e) {
            return null;
        }
        catch (NonUniqueResultException e)
        {
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
}
