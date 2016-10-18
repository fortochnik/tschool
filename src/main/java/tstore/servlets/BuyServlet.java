package tstore.servlets;

import tstore.model.CountryEntity;
import tstore.service.impl.CountryServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mipan on 18.10.2016.
 */
public class BuyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<CountryEntity> countryEntities = new CountryServiceImpl().getAll();
        request.setAttribute("countries", countryEntities);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/payment.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/");
    }
}
