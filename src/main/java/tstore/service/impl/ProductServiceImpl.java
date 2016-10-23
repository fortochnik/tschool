package tstore.service.impl;

import tstore.dao.ProductDao;
import tstore.dao.impl.ProductDaoImpl;
import tstore.model.ProductEntity;
import tstore.service.ProductService;

import java.util.List;
import java.util.Map;

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

    public int save(ProductEntity productEntity) {
        productDao.beginTransaction();
        productDao.persist(productEntity);
        productDao.closeTransaction();
        return productEntity.getId();
    }

    public List<ProductEntity> getBySearch(Map<String, String> searchParameters) {
        productDao.beginTransaction();
        List<ProductEntity> productEntities = productDao.findByCriteria(searchParameters);
        productDao.closeTransaction();
        return productEntities;
    }
}
