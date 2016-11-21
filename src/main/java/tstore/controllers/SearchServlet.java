package tstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tstore.model.CategoryEntity;
import tstore.model.ProductEntity;
import tstore.service.CategoryService;
import tstore.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mipan on 23.10.2016.
 */
@Controller
public class SearchServlet{

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST, value = "search")
    protected String doPost(Model model,
            @RequestParam(value = "form-category", required = false) String category,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price-min", required = false) String priceMin,
            @RequestParam(value = "price-max", required = false) String priceMax,
            @RequestParam(value = "company", required = false) String company,
            @RequestParam(value = "parameters", required = false) String parameters){

        Map<String, String> searchParameters = new HashMap<String, String>();
        searchParameters.put("category", category);
        searchParameters.put("name", name);
        searchParameters.put("priceMin", priceMin);
        searchParameters.put("priceMax", priceMax);
        searchParameters.put("company", company);
        searchParameters.put("parameters",  parameters);

        List<ProductEntity> productList = productService.getBySearch(searchParameters);

        model.addAttribute("categoryId", category);
        model.addAttribute("name", name);
        model.addAttribute("min", priceMin);
        model.addAttribute("max", priceMax);
        model.addAttribute("company", company);
        model.addAttribute("parameters", parameters);

        List<CategoryEntity> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("products", productList);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "search")
    protected String doGet(Model model){
        List<CategoryEntity> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "index";
    }
}
