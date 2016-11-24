package tstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.ProductDao;
import tstore.model.ProductEntity;
import tstore.service.ProductService;

import java.util.List;
import java.util.Map;

/**
 * Created by mipan on 09.10.2016.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    protected ProductDao productDao;

    /**
     * Get {@link ProductEntity} by id
     * @param id product id
     * @return target {@link ProductEntity}
     */
    public ProductEntity getProductById(Integer id) {
        ProductEntity productEntity = productDao.findById(ProductEntity.class, id);
        return productEntity;
    }

    /**
     * get all {@link ProductEntity}
     * @return {@link ProductEntity} list
     */
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> productEntityList = productDao.findAll(ProductEntity.class);
        return productEntityList;
    }

    /**
     * Update {@link ProductEntity}
     * @param product {@link ProductEntity} for update
     */
    public void update(ProductEntity product) {
        productDao.update(product);
    }

    /**
     * Save {@link ProductEntity}
     * @param productEntity {@link ProductEntity} for update
     * @return id of saved {@link ProductEntity}
     */
    public int save(ProductEntity productEntity) {
        productDao.persist(productEntity);
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
        List<ProductEntity> productEntities = productDao.findByCriteria(searchParameters);
        return productEntities;
    }
}
