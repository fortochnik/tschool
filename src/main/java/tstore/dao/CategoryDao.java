package tstore.dao;

import tstore.model.CategoryEntity;

/**
 * Created by mipan on 02.10.2016.
 */
public interface CategoryDao extends GenericDao<CategoryEntity, Integer> {

    void deleteById(int id);


}
