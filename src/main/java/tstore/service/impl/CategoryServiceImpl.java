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

//        categoryDao.deleteById(categoryId);

       /* for (String catId : categoriesId) {

            int categoryId = Integer.parseInt(catId);
            CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, categoryId);
            List<ProductEntity> productEntityList = productDao.getByCategoryId(categoryId);
            try {
                if (productEntityList.size() == 0) {

                    categoryDao.delete(categoryEntity);

                } else {
                    categoryDao.rollbackTransaction();
                    return categoryEntity.getName();
                }
            }
            catch (PersistenceException e)
            {
//                todo logging
                System.out.println("d");

            }
            catch (Exception e)
            {
                System.out.println("d");
            }

        }
        categoryDao.closeTransaction();*/
        categoryDao.closeTransaction();
        return null;
    }

    public void deleteById(int id) {
        categoryDao.beginTransaction();
        CategoryEntity categoryEntity = categoryDao.findById(CategoryEntity.class, id);
        categoryDao.delete(categoryEntity);
        categoryDao.closeTransaction();
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
