package tstore.servlets.admin;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import tstore.model.CategoryEntity;
import tstore.model.enums.Role;
import tstore.service.CategoryService;
import tstore.service.impl.CategoryServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by mipan on 22.10.2016.
 */
@Controller
public class CategoryManagerServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(CategoryManagerServlet.class);

    private CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {


            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            request.setAttribute("categoryexist", categories);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/category.jsp");
            rd.forward(request, response);
        } else {

            RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            List<String> NameNotDeleted = null;
            if (request.getParameter("update-form") != null) {
                updateNameCategory(parameterMap);
            }

            String[] categoryForDelete = request.getParameterValues("category-del-chbx");
            if (categoryForDelete != null) {
                NameNotDeleted = deleteCategory(categoryForDelete);
            }

            String[] categoryForCreate = request.getParameterValues("category-new");
            if (categoryForCreate != null) {

                createCategory(categoryForCreate);
            }
            if (NameNotDeleted != null) {
                request.setAttribute("notdeleted", NameNotDeleted);
            }

            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            request.setAttribute("categoryexist", categories);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/category.jsp");
            rd.forward(request, response);
        } else {

            RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);
        }
    }

    private void createCategory(String[] categoryForCreate) {
        for (String categoryName : categoryForCreate) {
            if (!categoryName.equals("")) {
                categoryService.create(categoryName);
            }
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

    private List<String> deleteCategory(String[] categoriesId) {
        List<String> notDeleted = new ArrayList<String>();
        CategoryService categoryService = new CategoryServiceImpl();
        for (String forDelete : categoriesId) {
            try {
                int categoryId = Integer.parseInt(forDelete);
                String name = categoryService.deleteById(categoryId);
                if (name != null) {
                    notDeleted.add(name);
                }

            } catch (NumberFormatException e) {
                logger.error(MessageFormat.format("Invalid value by deleted category : {0}", forDelete), e);
                throw new IllegalArgumentException("Category id must be numeric");
            }
        }
        return notDeleted;
    }
}
