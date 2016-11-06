package tstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.CategoryDao;
import tstore.dao.ProductDao;
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
    private CategoryDao categoryDaoImpl;


    /**
     * get all categories in db
     * @return all {@link CategoryEntity}
     */
    public List<CategoryEntity> getCategories() {
//        categoryDaoImpl.beginTransaction();
        List<CategoryEntity> categoryEntities = categoryDaoImpl.findAll(CategoryEntity.class);
//        categoryDaoImpl.closeTransaction();
        return categoryEntities;
    }

    /**
     * delete all {@link CategoryEntity}
     * @param categoriesId String categories id array
     *
     */
    public void deleteAll(String[] categoriesId) {
//        categoryDaoImpl.beginTransaction();
        int categoryId = Integer.parseInt(categoriesId[0]);
        CategoryEntity categoryEntity = categoryDaoImpl.findById(CategoryEntity.class, categoryId);
        categoryDaoImpl.delete(categoryEntity);
//        categoryDaoImpl.closeTransaction();

    }

    /**
     * Removes {@link CategoryEntity} by id
     * @param id category id
     * @return name of{@link CategoryEntity} if it didn't delete or null
     */
    public String deleteById(int id) {
//        categoryDaoImpl.beginTransaction();
        CategoryEntity categoryEntity = categoryDaoImpl.findById(CategoryEntity.class, id);
        if (categoryEntity.getProducts().size() == 0) {
            categoryDaoImpl.delete(categoryEntity);
//            categoryDaoImpl.closeTransaction();
        }
        else
        {
            String name = categoryEntity.getName();
//            categoryDaoImpl.closeTransaction();
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
//        categoryDaoImpl.beginTransaction();
        CategoryEntity categoryEntities = categoryDaoImpl.findById(CategoryEntity.class, id);
//        categoryDaoImpl.closeTransaction();
        return categoryEntities;
    }

    /**
     * Update current{@link CategoryEntity}
     * @param categoryEntity Entity for update
     */
    public void update(CategoryEntity categoryEntity) {
//        categoryDaoImpl.beginTransaction();
        categoryDaoImpl.update(categoryEntity);
//        categoryDaoImpl.closeTransaction();
    }

    /**
     * Create new {@link CategoryEntity}
     * @param categoryName name for new {@link CategoryEntity}
     */
    public void create(String categoryName) {
//        categoryDaoImpl.beginTransaction();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName);
        categoryEntity.setLevel(1);
        categoryDaoImpl.persist(categoryEntity);
//        categoryDaoImpl.closeTransaction();
    }
}
