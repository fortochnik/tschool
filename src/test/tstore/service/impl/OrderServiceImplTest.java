package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.OrderDao;
import tstore.dao.ProductDao;
import tstore.model.OrderEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 24.11.2016.
 */
public class OrderServiceImplTest {

    private OrderServiceImplUnitTest orderServiceImplUnitTest;

    @Before
    public void setUp() {
        orderServiceImplUnitTest = new OrderServiceImplUnitTest();
    }

    @Test
    public void testUpdate() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        orderServiceImplUnitTest.update(orderEntity);
        Mockito.verify(orderServiceImplUnitTest.getOrderDao(), Mockito.atLeastOnce()).update(orderEntity);

    }

    @Test
    public void testGetOrders() throws Exception {
        orderServiceImplUnitTest.getOrderDao();
        orderServiceImplUnitTest.getOrders("12", "login");
        Mockito.verify(orderServiceImplUnitTest.getOrderDao(), Mockito.atLeastOnce()).find("12", "login");

    }

    private class OrderServiceImplUnitTest extends OrderServiceImpl {
        public OrderServiceImplUnitTest() {
            super();
            productDao = mock(ProductDao.class);
            orderDao = mock(OrderDao.class);
        }

        public ProductDao getProductDao() {
            return productDao;
        }

        public OrderDao getOrderDao() {
            return orderDao;
        }

    }
}