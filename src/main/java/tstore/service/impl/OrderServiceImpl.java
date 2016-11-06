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
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * get {@link OrderEntity} with BASKET {@link BasketOrderState} state by User Id
     * @param userId user id
     * @return {@link OrderEntity} with BASKET {@link BasketOrderState} state
     */
    public OrderEntity getBasketByUserId(Integer userId) {
//        orderDao.beginTransaction();
        OrderEntity basket = orderDao.findBasketByUserId(userId);
        if (basket!=null) {
            Hibernate.initialize(basket.getProductList());
        }
//        orderDao.closeTransaction();
        return basket;
    }

    /**
     * Create {@link OrderEntity}
     * @param orderEntity {@link OrderEntity} for create
     */
    public void createOrder(OrderEntity orderEntity) {
//        orderDao.beginTransaction();
        orderDao.persist(orderEntity);
//        orderDao.closeTransaction();
    }

    /**
     * get {@link OrderEntity} list by user
     * @param userEntity user
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getOrdersByUser(UserEntity userEntity) {
//        orderDao.beginTransaction();

        List<OrderEntity> orderEntities = orderDao.findOrdersByUser(userEntity);
        for (OrderEntity orderEntity : orderEntities) {
            Hibernate.initialize(orderEntity.getProductList());
        }
//        orderDao.closeTransaction();
        return orderEntities;
    }

    /**
     * Update {@link OrderEntity}
     * @param orderEntity {@link OrderEntity} for update
     */
    public void update(OrderEntity orderEntity) {
//        orderDao.beginTransaction();
        orderDao.update(orderEntity);
//        orderDao.closeTransaction();
    }

    /**
     * Change status of Basket to Order in {@link OrderEntity} after buy process
     * @param basket {@link OrderEntity} for change status
     */
    public void updateBasketToOrder(OrderEntity basket) {
//        orderDao.beginTransaction();
//        ProductInBasketService productInBasketService = new ProductInBasketServiceImpl();
        Set<ProductListEntity> productList = basket.getProductList();
        for (ProductListEntity productListEntity : productList) {
            ProductEntity product = productListEntity.getProduct();
            productListEntity.setPrice(product.getPrice());
            int count = product.getCount() - productListEntity.getCount();
            product.setCount(count);
            productDao.update(product);
        }
        orderDao.update(basket);
//        orderDao.closeTransaction();
    }

    /**
     * Get all orders with target {@link OrderEntity} id and {@link UserEntity} email
     * if one of parameters is null the search will be use only another one
     * @param orderNumber {@link OrderEntity} id
     * @param userEmail{@link UserEntity} email
     * @return list of {@link OrderEntity}
     */
    public List<OrderEntity> getOrders(String orderNumber, String userEmail) {
//        orderDao.beginTransaction();
        List<OrderEntity> orderEntities= orderDao.find(orderNumber, userEmail);
//        orderDao.closeTransaction();
        return orderEntities;
    }

    /**
     * Get all {@link OrderEntity} that not delivery to client
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getNotDelivered() {
//        orderDao.beginTransaction();
        List<OrderEntity> orderEntities= orderDao.findNotDelivered();
//        orderDao.closeTransaction();
        return orderEntities;
    }

    /**
     * Get all {@link OrderEntity} that not paid
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getNotPaid() {
//        orderDao.beginTransaction();
        List<OrderEntity> orderEntities= orderDao.findNotPaid();
//        orderDao.closeTransaction();
        return orderEntities;
    }

    /**
     * Get all {@link OrderEntity}
     * @return {@link OrderEntity} list
     */
    public List<OrderEntity> getAllOrders() {
//        orderDao.beginTransaction();
        List<OrderEntity> orderEntities= orderDao.findAllOrders();
//        orderDao.closeTransaction();
        return orderEntities;
    }

}
