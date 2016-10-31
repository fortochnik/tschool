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
    private ProductDao productDaoImpl;

    /**
     * Get {@link ProductEntity} by id
     * @param id product id
     * @return target {@link ProductEntity}
     */
    public ProductEntity getProductById(Integer id) {
//        productDaoImpl.beginTransaction();
        ProductEntity productEntity = productDaoImpl.findById(ProductEntity.class, id);
//        productDaoImpl.closeTransaction();
        return productEntity;
    }

    /**
     * get all {@link ProductEntity}
     * @return {@link ProductEntity} list
     */
    public List<ProductEntity> getAllProducts() {
//        productDaoImpl.beginTransaction();
        List<ProductEntity> productEntityList = productDaoImpl.findAll(ProductEntity.class);
//        productDaoImpl.closeTransaction();
        return productEntityList;
    }

    /**
     * Update {@link ProductEntity}
     * @param product {@link ProductEntity} for update
     */
    public void update(ProductEntity product) {
//        productDaoImpl.beginTransaction();
        productDaoImpl.update(product);
//        productDaoImpl.closeTransaction();
    }

    /**
     * Save {@link ProductEntity}
     * @param productEntity {@link ProductEntity} for update
     * @return id of saved {@link ProductEntity}
     */
    public int save(ProductEntity productEntity) {
//        productDaoImpl.beginTransaction();
        productDaoImpl.persist(productEntity);
//        productDaoImpl.closeTransaction();
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
//        productDaoImpl.beginTransaction();
        List<ProductEntity> productEntities = productDaoImpl.findByCriteria(searchParameters);
//        productDaoImpl.closeTransaction();
        return productEntities;
    }
}
