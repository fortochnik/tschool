package tstore.servlets.admin;

import tstore.model.CategoryEntity;
import tstore.service.CategoryService;
import tstore.service.impl.CategoryServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mipan on 22.10.2016.
 */
public class CategoryManagerServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryService categoryService = new CategoryServiceImpl();
        List<CategoryEntity> categories = categoryService.getCategories();
        request.setAttribute("categoryexist", categories);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/category.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();

        if (request.getParameter("update-form") != null) {
            updateNameCategory(parameterMap);
        }


        String[] categoryForCreate = request.getParameterValues("category-new");
        if (categoryForCreate != null) {
            createCategory(categoryForCreate);
        }
/*    -----------*/

        String[] categoryForDelete = request.getParameterValues("category-del-chbx");
        if (categoryForDelete!=null) {
//            todo change fixed value
            deleteCategory(categoryForDelete);
        }


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/category.jsp");
        rd.forward(request, response);
    }

    private void createCategory(String[] categoryForCreate) {
        for (String categoryName : categoryForCreate) {
            categoryService.create(categoryName);
        }

    }

    private void updateNameCategory(Map<String, String[]> parameterMap) {

        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            if (stringEntry.getKey().contains("category-name-update")) {
                int id = Integer.parseInt(stringEntry.getKey().split("-")[3]);
                CategoryEntity categoryEntity = categoryService.get(id);
                categoryEntity.setName(stringEntry.getValue()[0]);
                categoryService.update(categoryEntity);
            }
        }
    }

    private String deleteCategory(String[] categoriesId) {
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.deleteById(4);
//        String notDeletedcategory = categoryService.deleteAll(categoriesId);
        return null;
    }
}
