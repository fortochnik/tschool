package tstore.service.impl;

import tstore.dao.ProductListDao;
import tstore.dao.impl.ProductListDaoImpl;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.service.ProductInBasketService;

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


/*    public ProductListEntity getProductInBasketById(Integer idProduct, OrderEntity orderEntity) {
        productListDao.beginTransaction();
        ProductListEntity productInBasketById = productListDao.findProductInBasketById(idProduct, orderEntity);
        productListDao.closeTransaction();
        return productInBasketById;
    }*/
}
