package tstore.servlets;

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
import tstore.service.impl.CategoryServiceImpl;
import tstore.service.impl.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mipan on 26.09.2016.
 */

@Controller
@RequestMapping("/")
public class MainServlet { //extends HttpServlet {
    final static Logger logger = Logger.getLogger(MainServlet.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView doGet() throws ServletException, IOException {

        List<CategoryEntity> categories = categoryService.getCategories();
//        request.setAttribute("categories", categories);

        List<ProductEntity> productList = productService.getAllProducts();
//        request.setAttribute("products", productList);
//        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
//        rd.forward(request, response);

        ModelAndView index = new ModelAndView("index");
        index.addObject("categories", categories);
        index.addObject("products", productList);
        return index;
    }
}
