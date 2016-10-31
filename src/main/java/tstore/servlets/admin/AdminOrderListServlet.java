package tstore.servlets.admin;

import org.springframework.stereotype.Controller;
import tstore.model.OrderEntity;
import tstore.model.enums.Role;
import tstore.service.OrderService;
import tstore.service.impl.OrderServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by mipan on 05.10.2016.
 */
@Controller
public class AdminOrderListServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        List<OrderEntity> orderEntityList = null;
        if (request.getParameter("is-custom-search") != null) {
            String orderNumber = request.getParameter("order-number");
            String userEmail = request.getParameter("email");
            orderEntityList = getCustomSearchResult(orderNumber, userEmail);
        }

        if (request.getParameter("all-not-delivered") != null) {
            orderEntityList = orderService.getNotDelivered();
        }

        if (request.getParameter("all-not-paid") != null) {
            orderEntityList = orderService.getNotPaid();
        }

        if (request.getParameter("orders-all") != null) {
            orderEntityList = orderService.getAllOrders();
        }


        request.setAttribute("orders", orderEntityList);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/orderslist.jsp");
        rd.forward(request, response);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {


        } else {

            rd = request.getRequestDispatcher("/");
            rd.forward(request, response);
        }
    }

    private List<OrderEntity> getCustomSearchResult(String orderNumber, String userEmail) {

        List<OrderEntity> orderEntities = orderService.getOrders(orderNumber, userEmail);
        return orderEntities;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {

        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") && (session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN)
                || session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE))) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/orderslist.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("/login");
        }

    }
    else
    {
        RequestDispatcher rd = request.getRequestDispatcher("/");
        rd.forward(request, response);
    }

}
}
