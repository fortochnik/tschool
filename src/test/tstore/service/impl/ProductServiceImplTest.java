package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.ProductDao;
import tstore.model.ProductEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 24.11.2016.
 */
public class ProductServiceImplTest {

    private ProductServiceImplUnitTest productServiceImplUnitTest;
    @Before
    public void setUp() throws Exception {
        productServiceImplUnitTest = new ProductServiceImplUnitTest();
    }

    @Test
    public void testGetProductById() throws Exception {
        productServiceImplUnitTest.getProductById(2);
        Mockito.verify(productServiceImplUnitTest.getProductDao(), Mockito.atLeastOnce()).findById(ProductEntity.class,2);
    }

    @Test
    public void testSave() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        productServiceImplUnitTest.save(productEntity);
        Mockito.verify(productServiceImplUnitTest.getProductDao(), Mockito.atLeastOnce()).persist(productEntity);
    }

    private class ProductServiceImplUnitTest extends ProductServiceImpl{
        public ProductServiceImplUnitTest() {
            super();
            productDao = mock(ProductDao.class);
        }
        public ProductDao getProductDao(){
            return productDao;
        }
    }
}