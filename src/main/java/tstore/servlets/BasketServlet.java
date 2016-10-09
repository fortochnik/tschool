package tstore.servlets;

import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mipan on 04.10.2016.
 */
public class BasketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        try {
            if (session.getAttribute(SessionAttributes.LOGIN).equals("true")){
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/basket.jsp");
                rd.forward(request, response);
            }
            else{
                response.sendRedirect("/login");
            }
        }
        catch (NullPointerException e)
        {

        }
    }
}