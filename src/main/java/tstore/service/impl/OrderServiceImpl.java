package tstore.service.impl;

import org.hibernate.Hibernate;
import tstore.dao.OrderDao;
import tstore.dao.impl.OrderDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.service.OrderService;
import tstore.service.ProductInBasketService;
import tstore.service.ProductService;

import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 11.10.2016.
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();

    public OrderEntity getBasketByUserId(Integer userId) {
        orderDao.beginTransaction();
        OrderEntity basket = orderDao.findBasketByUserId(userId);
        if (basket!=null) {
            Hibernate.initialize(basket.getProductList());
        }
        orderDao.closeTransaction();
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

    public void update(OrderEntity orderEntity) {
        orderDao.beginTransaction();
        orderDao.update(orderEntity);
        orderDao.closeTransaction();
    }

    public void updateBasketToOrder(OrderEntity basket) {
        orderDao.beginTransaction();
        ProductService productService = new ProductServiceImpl();
        ProductInBasketService productInBasketService = new ProductInBasketServiceImpl();
        Set<ProductListEntity> productList = basket.getProductList();
        for (ProductListEntity productListEntity : productList) {
            ProductEntity product = productListEntity.getProduct();
            productListEntity.setPrice(product.getPrice());
            int count = product.getCount() - productListEntity.getCount();
            product.setCount(count);
            productService.update(product);
            productInBasketService.update(productListEntity);
        }
        orderDao.update(basket);


        orderDao.closeTransaction();

    }

    public List<OrderEntity> getOrders(String orderNumber, String userEmail) {
        orderDao.beginTransaction();

        List<OrderEntity> orderEntities= orderDao.find(orderNumber, userEmail);

        orderDao.closeTransaction();
        return orderEntities;
    }

    public List<OrderEntity> getNotDelivered() {
        orderDao.beginTransaction();

        List<OrderEntity> orderEntities= orderDao.findNotDelivered();

        orderDao.closeTransaction();
        return orderEntities;
    }

    public List<OrderEntity> getNotPaid() {
        orderDao.beginTransaction();

        List<OrderEntity> orderEntities= orderDao.findPaid();

        orderDao.closeTransaction();
        return orderEntities;
    }

    public List<OrderEntity> getAllOrders() {
        orderDao.beginTransaction();
        List<OrderEntity> orderEntities= orderDao.findAllOrders();
        orderDao.closeTransaction();
        return orderEntities;
    }

}
