package tstore.service.impl;

import org.hibernate.Hibernate;
import tstore.dao.OrderDao;
import tstore.dao.impl.OrderDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.service.OrderService;

import java.util.List;

/**
 * Created by mipan on 11.10.2016.
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();

    public OrderEntity getBasketByUserId(Integer userId) {
        orderDao.beginTransaction();
//        List<OrderEntity> orderEntities = orderDao.findAll(OrderEntity.class);
        OrderEntity basket = orderDao.findBasketByUserId(userId);
        Hibernate.initialize(basket.getProductList());
        orderDao.closeTransaction();
//        return orderEntities.get(0);
        return basket;
    }

    public void createOrder(OrderEntity orderEntity) {
        orderDao.beginTransaction();
        orderDao.persist(orderEntity);
        orderDao.closeTransaction();
    }

    public List<OrderEntity> getOrdersByUser(UserEntity userEntity) {
        orderDao.beginTransaction();

        List<OrderEntity> orderEntities = orderDao.findOrdersByUser(userEntity);
        for (OrderEntity orderEntity : orderEntities) {
            Hibernate.initialize(orderEntity.getProductList());
        }
/*
//        Hibernate.initialize(userEntity.getOrders());

//        Set<OrderEntity> orderEntities = orderDao.findOrdersByUser(userEntity);
        for (OrderEntity orderEntity : orderEntities) {
            Hibernate.initialize(orderEntity.getProductList());
        }*/

        orderDao.closeTransaction();

        return orderEntities;
    }

}
