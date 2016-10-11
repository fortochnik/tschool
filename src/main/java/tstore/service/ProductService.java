package tstore.service;

import tstore.model.ProductEntity;

import java.util.List;

/**
 * Created by mipan on 09.10.2016.
 */
public interface ProductService {
    ProductEntity getProductById(Integer id);
    List<ProductEntity> getAllProducts();
    List<ProductEntity> getProductsByCategory();

}
