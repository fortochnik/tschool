package tstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.ImageDao;
import tstore.dao.ProductDao;
import tstore.dao.impl.ImageDaoImpl;
import tstore.dao.impl.ProductDaoImpl;
import tstore.model.ImageEntity;
import tstore.model.ProductEntity;
import tstore.service.ImageService;

import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    @Autowired
    protected ImageDao imageDao;
    @Autowired
    private ProductDao productDao;

    /**
     * Save url image for target product
     * @param url image's url
     * @param productId target product's id
     */
    public void save(String url, Integer productId) {
        ProductEntity productEntity = productDao.findById(ProductEntity.class, productId);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImage(url);
        imageEntity.setProduct(productEntity);
        imageDao.persist(imageEntity);
    }

    /**
     * Get url of main image for product
     * @param productId target product id
     * @return url of image
     */
    public ImageEntity getMain(int productId) {
        ImageEntity imageEntity =imageDao.getImage(productId, 1);
        return imageEntity;
    }

    /**
     * Get all url for target product
     * @param productId target product id
     * @return all image for product
     */
    public List<ImageEntity> getAll(int productId) {
        List<ImageEntity> imageEntity =imageDao.getImage(productId);
        return imageEntity;
    }
}
