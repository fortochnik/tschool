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

    public ProductListEntity getProductInBasketById(ProductEntity ProductEntity, OrderEntity orderEntity) {
        productListDao.beginTransaction();
        ProductListEntity productInBasketById = productListDao.findProductInBasketById(ProductEntity, orderEntity);
        productListDao.closeTransaction();
        return productInBasketById;
    }

    public void save(ProductListEntity productInBasketById) {
        productListDao.beginTransaction();
        productListDao.persist(productInBasketById);
        productListDao.closeTransaction();
    }

    public void update(ProductListEntity productInBasketById) {
        productListDao.beginTransaction();
        productListDao.update(productInBasketById);
        productListDao.closeTransaction();
    }

    public void delete(ProductListEntity productListEntity) {
        productListDao.beginTransaction();
        productListDao.delete(productListEntity);
        productListDao.closeTransaction();
    }

    public List getTopTenProduct() {
        productListDao.beginTransaction();

        List topTen = productListDao.getTopTen();
        Hibernate.initialize(topTen);
        productListDao.closeTransaction();
        return topTen;
    }

    public BigDecimal getProceedsBy(int numberOfDaysToLookBack) {
        productListDao.beginTransaction();
        BigDecimal proceeds = productListDao.getProceedsBy(numberOfDaysToLookBack);
        productListDao.closeTransaction();
        return proceeds;
    }

}
