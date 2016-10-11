package tstore.dao;

import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;

/**
 * Created by mipan on 02.10.2016.
 */
public interface ProductListDao extends GenericDao<ProductListEntity, Integer> {
    ProductListEntity findProductInBasketById(ProductEntity productEntity, OrderEntity orderEntity);
}
