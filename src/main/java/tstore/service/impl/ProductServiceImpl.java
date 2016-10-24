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

    /**
     * Get {@link ProductEntity} by id
     * @param id product id
     * @return target {@link ProductEntity}
     */
    public ProductEntity getProductById(Integer id) {
        productDao.beginTransaction();
        ProductEntity productEntity = productDao.findById(ProductEntity.class, id);
        productDao.closeTransaction();
        return productEntity;
    }

    /**
     * get all {@link ProductEntity}
     * @return {@link ProductEntity} list
     */
    public List<ProductEntity> getAllProducts() {
        productDao.beginTransaction();
        List<ProductEntity> productEntityList = productDao.findAll(ProductEntity.class);
        productDao.closeTransaction();
        return productEntityList;
    }

    /**
     * Update {@link ProductEntity}
     * @param product {@link ProductEntity} for update
     */
    public void update(ProductEntity product) {
        productDao.beginTransaction();
        productDao.update(product);
        productDao.closeTransaction();
    }

    /**
     * Save {@link ProductEntity}
     * @param productEntity {@link ProductEntity} for update
     * @return id of saved {@link ProductEntity}
     */
    public int save(ProductEntity productEntity) {
        productDao.beginTransaction();
        productDao.persist(productEntity);
        productDao.closeTransaction();
        return productEntity.getId();
    }

    /**
     * Get {@link ProductEntity} list by search parameters
     * @param searchParameters search parameter map {@param searchParameters.key} is search parameter name,
     * {@param searchParameters.value} is search value
     *
     * @return {@link ProductEntity} list by search parameters
     */
    public List<ProductEntity> getBySearch(Map<String, String> searchParameters) {
        productDao.beginTransaction();
        List<ProductEntity> productEntities = productDao.findByCriteria(searchParameters);
        productDao.closeTransaction();
        return productEntities;
    }
}
