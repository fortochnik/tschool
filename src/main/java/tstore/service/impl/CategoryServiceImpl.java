package tstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.CategoryDao;
import tstore.model.CategoryEntity;

import tstore.service.CategoryService;
import java.util.List;

/**
 * Service for management {@link CategoryEntity}
 *
 * Created by mipan on 22.10.2016.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    protected CategoryDao categoryDao;


    /**
     * get all categories in db
     * @return all {@link CategoryEntity}
     */
    public List<CategoryEntity> getCategories() {
        List<CategoryEntity> categoryEntities = categoryDao.findAll(CategoryEntity.class);
        return categoryEntities;
    }

    /**
     * delete all {@link CategoryEntity}
     * @param categoriesId String categories id array
     *
     */
    public void deleteAll(String[] categoriesId) {
        int categoryId = Integer.parseInt(categoriesId[0]);
        CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, categoryId);
        categoryDao.delete(categoryEntity);

    }

    /**
     * Removes {@link CategoryEntity} by id
     * @param id category id
     * @return name of{@link CategoryEntity} if it didn't delete or null
     */
    public String deleteById(int id) {
        CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, id);
        if (categoryEntity.getProducts().size() == 0) {
            categoryDao.delete(categoryEntity);
        }
        else
        {
            String name = categoryEntity.getName();
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
        CategoryEntity categoryEntities = categoryDao.findById(CategoryEntity.class, id);
        return categoryEntities;
    }

    /**
     * Update current{@link CategoryEntity}
     * @param categoryEntity Entity for update
     */
    public void update(CategoryEntity categoryEntity) {
        categoryDao.update(categoryEntity);
    }

    /**
     * Create new {@link CategoryEntity}
     * @param categoryName name for new {@link CategoryEntity}
     */
    public void create(String categoryName) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName);
        categoryEntity.setLevel(1);
        categoryDao.persist(categoryEntity);
    }
}
