package tstore.dao;

import tstore.model.OrderEntity;

/**
 * Created by mipan on 28.09.2016.
 */
public interface OrderDao extends GenericDao<OrderEntity, Integer> {
    OrderEntity findBasketByUserId(Integer userId);
}
