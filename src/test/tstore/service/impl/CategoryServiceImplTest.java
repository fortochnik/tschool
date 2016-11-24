package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.CategoryDao;
import tstore.model.CategoryEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 24.11.2016.
 */
public class CategoryServiceImplTest {
    CategoryServiceImplUnitTest categoryServiceImplUnitTest;

    @Before
    public void setUp() {
        categoryServiceImplUnitTest = new CategoryServiceImplUnitTest();
    }

    @Test
    public void testGetCategories() throws Exception {
        categoryServiceImplUnitTest.getCategories();
        Mockito.verify(categoryServiceImplUnitTest.getCategoryDao(), Mockito.atLeastOnce()).findAll(CategoryEntity.class);
    }

    @Test
    public void testGet() throws Exception {
        categoryServiceImplUnitTest.get(3);
        Mockito.verify(categoryServiceImplUnitTest.getCategoryDao(), Mockito.atLeastOnce()).findById(CategoryEntity.class,3);

    }

    private class CategoryServiceImplUnitTest extends CategoryServiceImpl {
        public CategoryServiceImplUnitTest() {
            super();
            categoryDao = mock(CategoryDao.class);
        }

        public CategoryDao getCategoryDao() {
            return categoryDao;
        }
    }
}