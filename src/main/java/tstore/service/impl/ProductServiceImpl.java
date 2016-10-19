package tstore.service.impl;

import tstore.dao.ProductDao;
import tstore.dao.impl.ProductDaoImpl;
import tstore.model.ProductEntity;
import tstore.service.ProductService;

import java.util.List;

/**
 * Created by mipan on 09.10.2016.
 */
public class ProductServiceImpl implements ProductService{

    private ProductDao productDao = new ProductDaoImpl();

    public ProductEntity getProductById(Integer id) {
        productDao.beginTransaction();
        ProductEntity productEntity = productDao.findById(ProductEntity.class, id);
        productDao.closeTransaction();
        return productEntity;
    }

    public List<ProductEntity> getAllProducts() {
        productDao.beginTransaction();
        List<ProductEntity> productEntityList = productDao.findAll(ProductEntity.class);
        productDao.closeTransaction();
        return productEntityList;
    }

    public List<ProductEntity> getProductsByCategory() {
        return null;
    }

    public void update(ProductEntity product) {
        productDao.beginTransaction();
        productDao.update(product);
        productDao.closeTransaction();
    }
}
