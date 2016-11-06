package tstore.servlets.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class CategoryManagerServlet{
    final static Logger logger = Logger.getLogger(CategoryManagerServlet.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "category", method = RequestMethod.GET)
    protected String doGet(Model model, HttpSession session){
//        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {


//            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            model.addAttribute("categoryexist", categories);

            return "admin/category";
            /*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/category.jsp");
            rd.forward(request, response);*/
        }
        else
        {
            return "redirect:/";

            /*RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);*/
        }
    }

    @RequestMapping(value = "category", method = RequestMethod.POST)
    protected String doPost(Model model,
                            @RequestParam(value = "update-form", required = false) String updateForm,
                            HttpSession session,HttpServletRequest request){
//        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            List<String> NameNotDeleted = null;
            if (updateForm != null) {
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

//            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            request.setAttribute("categoryexist", categories);

            return "admin/category";
        }
        else
        {
            return "redirect:/";
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

        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> stringEntry : entries) {
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
//        CategoryService categoryService = new CategoryServiceImpl();
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
