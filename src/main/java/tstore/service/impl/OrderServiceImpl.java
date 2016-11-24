package tstore.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.OrderDao;
import tstore.dao.ProductDao;
import tstore.dao.impl.OrderDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;
import tstore.service.OrderService;
import tstore.service.ProductInBasketService;
import tstore.service.ProductService;

import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 11.10.2016.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    protected ProductDao productDao;
    @Autowired
    protected OrderDao orderDao;

    /**
     * get {@link OrderEntity} with BASKET {@link BasketOrderState} state by User Id
     * @param userId user id
     * @return {@link OrderEntity} with BASKET {@link BasketOrderState} state
     */
    public OrderEntity getBasketByUserId(Integer userId) {
        OrderEntity basket = orderDao.findBasketByUserId(userId);
        if (basket!=null) {
            Hibernate.initialize(basket.getProductList());
        }
        return basket;
    }

    /**
     * Create {@link OrderEntity}
     * @param orderEntity {@link OrderEntity} for create
     */
    public void createOrder(OrderEntity orderEntity) {
        orderDao.persist(orderEntity);
    }

    /**
     * get {@link OrderEntity} list by user
     * @param userEntity user
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getOrdersByUser(UserEntity userEntity) {
        List<OrderEntity> orderEntities = orderDao.findOrdersByUser(userEntity);
        for (OrderEntity orderEntity : orderEntities) {
            Hibernate.initialize(orderEntity.getProductList());
        }
        return orderEntities;
    }

    /**
     * Update {@link OrderEntity}
     * @param orderEntity {@link OrderEntity} for update
     */
    public void update(OrderEntity orderEntity) {
        orderDao.update(orderEntity);
    }

    /**
     * Change status of Basket to Order in {@link OrderEntity} after buy process
     * @param basket {@link OrderEntity} for change status
     */
    public void updateBasketToOrder(OrderEntity basket) {
        Set<ProductListEntity> productList = basket.getProductList();
        for (ProductListEntity productListEntity : productList) {
            ProductEntity product = productListEntity.getProduct();
            productListEntity.setPrice(product.getPrice());
            int count = product.getCount() - productListEntity.getCount();
            product.setCount(count);
            productDao.update(product);
        }
        orderDao.update(basket);
    }

    /**
     * Get all orders with target {@link OrderEntity} id and {@link UserEntity} email
     * if one of parameters is null the search will be use only another one
     * @param orderNumber {@link OrderEntity} id
     * @param userEmail{@link UserEntity} email
     * @return list of {@link OrderEntity}
     */
    public List<OrderEntity> getOrders(String orderNumber, String userEmail) {
        List<OrderEntity> orderEntities= orderDao.find(orderNumber, userEmail);
        return orderEntities;
    }

    /**
     * Get all {@link OrderEntity} that not delivery to client
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getNotDelivered() {
        List<OrderEntity> orderEntities= orderDao.findNotDelivered();
        return orderEntities;
    }

    /**
     * Get all {@link OrderEntity} that not paid
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getNotPaid() {
        List<OrderEntity> orderEntities= orderDao.findNotPaid();
        return orderEntities;
    }

    /**
     * Get all {@link OrderEntity}
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getAllOrders() {
        List<OrderEntity> orderEntities= orderDao.findAllOrders();
        return orderEntities;
    }

}
