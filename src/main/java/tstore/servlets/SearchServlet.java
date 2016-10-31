package tstore.servlets;

import org.springframework.stereotype.Controller;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mipan on 23.10.2016.
 */
@Controller
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> searchParameters = new HashMap<String, String>();
        searchParameters.put("category", request.getParameter("form-category"));
        searchParameters.put("name", request.getParameter("name"));
        searchParameters.put("priceMin", request.getParameter("price-min"));
        searchParameters.put("priceMax", request.getParameter("price-max"));
        searchParameters.put("company", request.getParameter("company"));
        searchParameters.put("parameters",  request.getParameter("parameters"));

        ProductService productService = new ProductServiceImpl();
        List<ProductEntity> productList = productService.getBySearch(searchParameters);


        request.setAttribute("categoryId", request.getParameter("form-category"));
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("min", request.getParameter("price-min"));
        request.setAttribute("max", request.getParameter("price-max"));
        request.setAttribute("company", request.getParameter("company"));
        request.setAttribute("parameters", request.getParameter("parameters"));



        CategoryService categoryService = new CategoryServiceImpl();
        List<CategoryEntity> categories = categoryService.getCategories();
        request.setAttribute("categories", categories);

        request.setAttribute("products", productList);
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        List<CategoryEntity> categories = categoryService.getCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
}
