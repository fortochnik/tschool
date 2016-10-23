package tstore.service.impl;

import org.hibernate.query.Query;
import tstore.dao.CategoryDao;
import tstore.dao.ProductDao;
import tstore.dao.impl.CategoryDaoImpl;
import tstore.dao.impl.ProductDaoImpl;
import tstore.model.CategoryEntity;
import tstore.model.ProductEntity;
import tstore.service.CategoryService;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by mipan on 22.10.2016.
 */
public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    public List<CategoryEntity> getCategories() {
        categoryDao.beginTransaction();
        List<CategoryEntity> categoryEntities = categoryDao.findAll(CategoryEntity.class);
        categoryDao.closeTransaction();
        return categoryEntities;
    }

    public String deleteAll(String[] categoriesId) {
        categoryDao.beginTransaction();
        int categoryId = Integer.parseInt(categoriesId[0]);
        CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, categoryId);
        categoryDao.delete(categoryEntity);
        categoryDao.closeTransaction();
        return null;
    }

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

    public CategoryEntity get(int id) {
        categoryDao.beginTransaction();
        CategoryEntity categoryEntities = categoryDao.findById(CategoryEntity.class, id);
        categoryDao.closeTransaction();
        return categoryEntities;
    }

    public void update(CategoryEntity categoryEntity) {
        categoryDao.beginTransaction();
        categoryDao.update(categoryEntity);
        categoryDao.closeTransaction();
    }

    public void create(String categoryName) {
        categoryDao.beginTransaction();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName);
        categoryEntity.setLevel(1);
        categoryDao.persist(categoryEntity);
        categoryDao.closeTransaction();
    }
}
