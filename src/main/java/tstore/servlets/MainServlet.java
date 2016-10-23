package tstore.servlets;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by mipan on 26.09.2016.
 */
public class MainServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        List<CategoryEntity> categories = categoryService.getCategories();
        request.setAttribute("categories", categories);

        ProductService productService = new ProductServiceImpl();
        List<ProductEntity> productList = productService.getAllProducts();
        request.setAttribute("products", productList);
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
}
