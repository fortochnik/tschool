package tstore.service.impl;

import tstore.dao.ImageDao;
import tstore.dao.impl.ImageDaoImpl;
import tstore.dao.impl.ProductDaoImpl;
import tstore.model.ImageEntity;
import tstore.model.ProductEntity;
import tstore.service.ImageService;

import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
public class ImageServiceImpl implements ImageService {
    ImageDao imageDao = new ImageDaoImpl();

    /**
     * Save url image for target product
     * @param url image's url
     * @param productId target product's id
     */
    public void save(String url, Integer productId) {
        imageDao.beginTransaction();
        ProductEntity productEntity = new ProductDaoImpl().findById(ProductEntity.class, productId);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImage(url);
        imageEntity.setProduct(productEntity);
        imageDao.persist(imageEntity);
        imageDao.closeTransaction();
    }

    /**
     * Get url of main image for product
     * @param productId target product id
     * @return url of image
     */
    public ImageEntity getMain(int productId) {
        imageDao.beginTransaction();
        ImageEntity imageEntity =imageDao.getImage(productId, 1);
        imageDao.closeTransaction();
        return imageEntity;
    }

    /**
     * Get all url for target product
     * @param productId target product id
     * @return all image for product
     */
    public List<ImageEntity> getAll(int productId) {
        imageDao.beginTransaction();
        List<ImageEntity> imageEntity =imageDao.getImage(productId);
        imageDao.closeTransaction();
        return imageEntity;
    }
}
