package tstore.service.impl;

import org.hibernate.Hibernate;
import tstore.dao.ProductListDao;
import tstore.dao.impl.ProductListDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.service.ProductInBasketService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mipan on 11.10.2016.
 */
public class ProductInBasketServiceImpl implements ProductInBasketService {
    private ProductListDao productListDao = new ProductListDaoImpl();

    /**
     * Get products in target basket by {@link ProductEntity}
     * @param ProductEntity {@link ProductEntity} that look up in basket
     * @param orderEntity target basket
     * @return {@link ProductListEntity} for target {@param orderEntity} with {@param ProductEntity } or null
     */
    public ProductListEntity getProductInBasketById(ProductEntity ProductEntity, OrderEntity orderEntity) {
        productListDao.beginTransaction();
        ProductListEntity productInBasketById = productListDao.findProductInBasketById(ProductEntity, orderEntity);
        productListDao.closeTransaction();
        return productInBasketById;
    }

    /**
     * Save {@param ProductListEntity}
     * @param productInBasketById {@param ProductListEntity} for save
     */
    public void save(ProductListEntity productInBasketById) {
        productListDao.beginTransaction();
        productListDao.persist(productInBasketById);
        productListDao.closeTransaction();
    }

    /**
     * Upate {@param ProductListEntity}
     * @param productInBasketById {@param ProductListEntity} for update
     */
    public void update(ProductListEntity productInBasketById) {
        productListDao.beginTransaction();
        productListDao.update(productInBasketById);
        productListDao.closeTransaction();
    }


    /**
     * delete {@param ProductListEntity}
     * @param productListEntity {@param ProductListEntity} for delete
     */
    public void delete(ProductListEntity productListEntity) {
        productListDao.beginTransaction();
        productListDao.delete(productListEntity);
        productListDao.closeTransaction();
    }

    /**
     * get top 10 buyable products
     * @return list of {@link ProductListEntity} list
     */
    public List<ProductListEntity> getTopTenProduct() {
        productListDao.beginTransaction();

        List topTen = productListDao.getTopTen();
        Hibernate.initialize(topTen);
        productListDao.closeTransaction();
        return topTen;
    }

    /**
     * Get Proceeds by last X days
     * @param numberOfDaysToLookBack number of days for get proceeds
     * @return Proceeds by last {@param numberOfDaysToLookBack} days
     */
    public BigDecimal getProceedsBy(int numberOfDaysToLookBack) {
        productListDao.beginTransaction();
        BigDecimal proceeds = productListDao.getProceedsBy(numberOfDaysToLookBack);
        productListDao.closeTransaction();
        return proceeds;
    }

}
