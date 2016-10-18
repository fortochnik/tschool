package tstore.service;

import tstore.model.OrderEntity;
import tstore.model.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 11.10.2016.
 */
public interface OrderService {
    OrderEntity getBasketByUserId(Integer userId);
    void createOrder(OrderEntity orderEntity);

    List<OrderEntity> getOrdersByUser(UserEntity userEntity);

    void update(OrderEntity basketByUserId);
}
