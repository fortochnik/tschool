package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.ImageDao;
import tstore.model.ImageEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by mpankin on 24.10.2016.
 */
public class ImageServiceImplTest {
    ImageServiceImplUnitTest imageServiceImplUnitTest;
    private class ImageServiceImplUnitTest extends ImageServiceImpl{
        public ImageServiceImplUnitTest() {
            super();
            imageDao = mock(ImageDao.class);
        }
        public ImageDao  getImageDao(){
            return imageDao;
        }
    }

    @Before
    public void setUp() throws Exception {
        imageServiceImplUnitTest = new ImageServiceImplUnitTest();
    }

    @Test
    public void testGetMain() throws Exception {
        int productId = 1;
        imageServiceImplUnitTest.getMain(productId);
        int imageMainIndex = 1;
        Mockito.verify(imageServiceImplUnitTest.getImageDao(), Mockito.atLeastOnce()).getImage(productId, imageMainIndex);
    }

    @Test
    public void testGetAll() throws Exception {
        int productId = 1;
        imageServiceImplUnitTest.getAll(productId);
        Mockito.verify(imageServiceImplUnitTest.getImageDao(), Mockito.atLeastOnce()).getImage(productId);
    }
}