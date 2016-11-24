package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.ProductListDao;
import tstore.model.ProductListEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 24.11.2016.
 */
public class ProductInBasketServiceImplTest {

    private ProductInBasketServiceImplUnitTest productInBasketServiceImplUnitTest;

    @Before
    public void setUp() throws Exception {
        productInBasketServiceImplUnitTest = new ProductInBasketServiceImplUnitTest();
    }

    @Test
    public void testDelete() throws Exception {
        ProductListEntity productListEntity = new ProductListEntity();
        productInBasketServiceImplUnitTest.delete(productListEntity);
        Mockito.verify(productInBasketServiceImplUnitTest.getProductListDao(), Mockito.atLeastOnce()).delete(productListEntity);
    }

    @Test
    public void testGetBasketProductCount() throws Exception {
        productInBasketServiceImplUnitTest.getBasketProductCount(5);
        Mockito.verify(productInBasketServiceImplUnitTest.getProductListDao(), Mockito.atLeastOnce()).getBasketProductCountByUserId(5);
    }

    private class ProductInBasketServiceImplUnitTest extends ProductInBasketServiceImpl {
        public ProductInBasketServiceImplUnitTest() {
            super();
            productListDao = mock(ProductListDao.class);
        }
        public ProductListDao getProductListDao(){
            return productListDao;
        }
    }
}