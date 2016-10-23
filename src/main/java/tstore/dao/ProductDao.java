package tstore.dao;

import tstore.model.ProductEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by mipan on 28.09.2016.
 */
public interface ProductDao extends GenericDao<ProductEntity, Integer> {
    List<ProductEntity> getByCategoryId(int categoryId);

    List<ProductEntity> findByCriteria(Map<String, String> searchParameters);
}
