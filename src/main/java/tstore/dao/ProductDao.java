package tstore.dao;

import tstore.model.ProductEntity;

import java.util.List;

/**
 * Created by mipan on 28.09.2016.
 */
public interface ProductDao extends GenericDao<ProductEntity, Integer> {
    List<ProductEntity> getByCategoryId(int categoryId);
}
