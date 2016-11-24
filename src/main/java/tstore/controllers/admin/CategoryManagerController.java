package tstore.controllers.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tstore.model.CategoryEntity;
import tstore.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by mipan on 22.10.2016.
 */
@Controller
public class CategoryManagerController {
    final static Logger logger = Logger.getLogger(CategoryManagerController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "category", method = RequestMethod.GET)
    protected String doGet(Model model){
            List<CategoryEntity> categories = categoryService.getCategories();
            model.addAttribute("categoryexist", categories);

            return "admin/category";
    }

    @RequestMapping(value = "category", method = RequestMethod.POST)
    protected String doPost(Model model,
                            @RequestParam(value = "update-form", required = false) String updateForm,
                            HttpServletRequest request){
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
            List<CategoryEntity> categories = categoryService.getCategories();
            request.setAttribute("categoryexist", categories);

            return "admin/category";
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
