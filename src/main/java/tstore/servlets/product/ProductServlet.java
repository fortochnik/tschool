package tstore.servlets.product;

import tstore.exceptions.PageNotFoundException;
import tstore.model.ProductEntity;
import tstore.service.ProductService;
import tstore.service.impl.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mipan on 25.09.2016.
 */
public class ProductServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        ProductService productService = new ProductServiceImpl();

        int productId = getProductIdFromUri(request.getRequestURI());
        ProductEntity product = productService.getProductById(productId);
        if (product == null){
//            todo logging
            throw new PageNotFoundException();
        }

        request.setAttribute("product", product);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/product/product.jsp");
        rd.forward(request, response);
    }

    private int getProductIdFromUri(String requestURI) {
        int productId;
        String[] split = requestURI.split("/");
        try {
            productId = Integer.parseInt(split[split.length - 1]);
        }
        catch (NumberFormatException e)
        {
            throw new PageNotFoundException(e.getMessage());
//            todo logging
        }
        return productId;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("/");
    }
}
