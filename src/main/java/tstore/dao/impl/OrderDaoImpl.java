package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.OrderDao;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;
import tstore.utils.SessionAttributes;

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
}
