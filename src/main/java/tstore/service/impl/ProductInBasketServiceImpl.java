package tstore.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Service
@Transactional
public class ProductInBasketServiceImpl implements ProductInBasketService {
    @Autowired
    private ProductListDao productListDao;

    /**
     * Get products in target basket by {@link ProductEntity}
     * @param ProductEntity {@link ProductEntity} that look up in basket
     * @param orderEntity target basket
     * @return {@link ProductListEntity} for target {@param orderEntity} with {@param ProductEntity } or null
     */
    public ProductListEntity getProductInBasketById(ProductEntity ProductEntity, OrderEntity orderEntity) {
        ProductListEntity productInBasketById = productListDao.findProductInBasketById(ProductEntity, orderEntity);
        return productInBasketById;
    }

    /**
     * Save {@param ProductListEntity}
     * @param productInBasketById {@param ProductListEntity} for save
     */
    public void save(ProductListEntity productInBasketById) {
        productListDao.persist(productInBasketById);
    }

    /**
     * Upate {@param ProductListEntity}
     * @param productInBasketById {@param ProductListEntity} for update
     */
    public void update(ProductListEntity productInBasketById) {
        productListDao.update(productInBasketById);
    }


    /**
     * delete {@param ProductListEntity}
     * @param productListEntity {@param ProductListEntity} for delete
     */
    public void delete(ProductListEntity productListEntity) {
        productListDao.delete(productListEntity);
    }

    /**
     * get top 10 buyable products
     * @return list of {@link ProductListEntity} list
     */
    public List getTopTenProduct() {
        List topTen = productListDao.getTopTen();
        Hibernate.initialize(topTen);
        return topTen;
    }

    /**
     * Get Proceeds by last X days
     * @param numberOfDaysToLookBack number of days for get proceeds
     * @return Proceeds by last {@param numberOfDaysToLookBack} days
     */
    public BigDecimal getProceedsBy(int numberOfDaysToLookBack) {
        BigDecimal proceeds = productListDao.getProceedsBy(numberOfDaysToLookBack);
        return proceeds;
    }

    @Override
    public int getBasketProductCount(Integer userId) {
        return productListDao.getBasketProductCountByUserId(userId);
    }

}
