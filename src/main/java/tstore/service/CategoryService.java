package tstore.service;

import tstore.model.CategoryEntity;

import java.util.List;

/**
 * Created by mipan on 22.10.2016.
 */
public interface CategoryService {
    List<CategoryEntity> getCategories();

    void deleteAll(String[] categoriesId);

    String deleteById(int id);

    CategoryEntity get(int id);

    void update(CategoryEntity categoryEntity);

    void create(String categoryName);
}
