package tstore.service;

import tstore.model.OrderEntity;

/**
 * Created by mipan on 11.10.2016.
 */
public interface OrderService {
    OrderEntity getBasketByUserId(Integer userId);
    void createOrder(OrderEntity orderEntity);
}
