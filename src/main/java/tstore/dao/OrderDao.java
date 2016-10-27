package tstore.dao;

import tstore.model.OrderEntity;
import tstore.model.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 28.09.2016.
 */
public interface OrderDao extends GenericDao<OrderEntity, Integer> {
    OrderEntity findBasketByUserId(Integer userId);

    List findOrdersByUser(UserEntity userEntity);

    List<OrderEntity> find(String orderNumber, String userEmail);

    List<OrderEntity> findNotDelivered();

    List<OrderEntity> findPaid();
    List<OrderEntity> findNotPaid();

    List<OrderEntity> findAllOrders();
}
