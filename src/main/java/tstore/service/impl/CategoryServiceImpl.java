package tstore.service.impl;

import tstore.dao.CategoryDao;
import tstore.dao.ProductDao;
import tstore.dao.impl.CategoryDaoImpl;
import tstore.dao.impl.ProductDaoImpl;
import tstore.model.CategoryEntity;

import tstore.service.CategoryService;
import java.util.List;

/**
 * Service for management {@link CategoryEntity}
 *
 * Created by mipan on 22.10.2016.
 */
public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    /**
     * get all categories in db
     * @return all {@link CategoryEntity}
     */
    public List<CategoryEntity> getCategories() {
        categoryDao.beginTransaction();
        List<CategoryEntity> categoryEntities = categoryDao.findAll(CategoryEntity.class);
        categoryDao.closeTransaction();
        return categoryEntities;
    }

    /**
     * delete all {@link CategoryEntity}
     * @param categoriesId String categories id array
     *
     */
    public void deleteAll(String[] categoriesId) {
        categoryDao.beginTransaction();
        int categoryId = Integer.parseInt(categoriesId[0]);
        CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, categoryId);
        categoryDao.delete(categoryEntity);
        categoryDao.closeTransaction();

    }

    /**
     * Removes {@link CategoryEntity} by id
     * @param id category id
     * @return name of{@link CategoryEntity} if it didn't delete or null
     */
    public String deleteById(int id) {
        categoryDao.beginTransaction();
        CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, id);
        if (categoryEntity.getProducts().size() == 0) {
            categoryDao.delete(categoryEntity);
            categoryDao.closeTransaction();
        }
        else
        {
            String name = categoryEntity.getName();
            categoryDao.closeTransaction();
            return name;
        }
        return null;
    }

    /**
     * Get {@link CategoryEntity} by id
     * @param id category id
     * @return {@link CategoryEntity} with current id
     */
    public CategoryEntity get(int id) {
        categoryDao.beginTransaction();
        CategoryEntity categoryEntities = categoryDao.findById(CategoryEntity.class, id);
        categoryDao.closeTransaction();
        return categoryEntities;
    }

    /**
     * Update current{@link CategoryEntity}
     * @param categoryEntity Entity for update
     */
    public void update(CategoryEntity categoryEntity) {
        categoryDao.beginTransaction();
        categoryDao.update(categoryEntity);
        categoryDao.closeTransaction();
    }

    /**
     * Create new {@link CategoryEntity}
     * @param categoryName name for new {@link CategoryEntity}
     */
    public void create(String categoryName) {
        categoryDao.beginTransaction();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName);
        categoryEntity.setLevel(1);
        categoryDao.persist(categoryEntity);
        categoryDao.closeTransaction();
    }
}
