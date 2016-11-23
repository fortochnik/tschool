package tstore.service;

import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mipan on 11.10.2016.
 */
public interface ProductInBasketService {
    ProductListEntity getProductInBasketById(ProductEntity ProductEntity, OrderEntity orderEntity);


    void save(ProductListEntity productInBasketById);

    void update(ProductListEntity productInBasketById);

    void delete(ProductListEntity productListEntity);

    List getTopTenProduct();

    BigDecimal getProceedsBy(int numberOfDaysToLookBack);


    int getBasketProductCount(Integer attribute);
}
