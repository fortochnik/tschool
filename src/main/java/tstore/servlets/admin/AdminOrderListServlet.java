package tstore.servlets.admin;

import tstore.model.enums.Role;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mipan on 05.10.2016.
 */
public class AdminOrderListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("false")) {
            response.sendRedirect("/login");

        }

//        String roleAttribute = (String) session.getAttribute(SessionAttributes.ROLE);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") && (SessionAttributes.ROLE.equals(Role.ADMIN)
                || SessionAttributes.ROLE.equals(Role.EMPLOYEE)))
        {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/listOrder.jsp");
            rd.forward(request, response);
        }
        else
        {
            response.sendRedirect("/");
        }

    }
}
