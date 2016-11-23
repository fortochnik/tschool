package tstore.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tstore.model.CategoryEntity;
import tstore.model.ProductEntity;
import tstore.service.CategoryService;
import tstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by mipan on 26.09.2016.
 */

@Controller
@RequestMapping("/")
public class MainServlet {
    final static Logger logger = Logger.getLogger(MainServlet.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet(HttpSession session){

        List<CategoryEntity> categories = categoryService.getCategories();

        List<ProductEntity> productList = productService.getAllProducts();

        ModelAndView index = new ModelAndView("index");
        index.addObject("categories", categories);
        index.addObject("products", productList);
        return index;
    }
}
