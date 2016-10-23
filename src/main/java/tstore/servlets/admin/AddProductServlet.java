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
import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryService categoryService = new CategoryServiceImpl();
        List<CategoryEntity> categories = categoryService.getCategories();
        request.setAttribute("categories", categories);

//        request.setAttribute("orders", orderEntityList);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/addProduct.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
