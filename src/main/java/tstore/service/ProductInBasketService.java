package tstore.service;

import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;

/**
 * Created by mipan on 11.10.2016.
 */
public interface ProductInBasketService {
    ProductListEntity getProductInBasketById(ProductEntity ProductEntity, OrderEntity orderEntity);


    void save(ProductListEntity productInBasketById);

    void update(ProductListEntity productInBasketById);
}
