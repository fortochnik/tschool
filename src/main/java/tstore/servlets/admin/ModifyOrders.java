package tstore.servlets.admin;

import org.springframework.stereotype.Controller;
import tstore.model.OrderEntity;
import tstore.model.enums.OrderStatus;
import tstore.model.enums.PaymentStatus;
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
 * Created by mipan on 23.10.2016.
 */
@Controller
public class ModifyOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {
        OrderService orderService = new OrderServiceImpl();
        OrderEntity order = null;
        String modifyOrderId = request.getParameter("modify");
        String saveOrderId = request.getParameter("order-update-admin");
        String paymentStatus = request.getParameter("form-payment-status");
        String deliveryStatus = request.getParameter("form-delivery-status");
        if (modifyOrderId !=null){
            List<OrderEntity> orders = orderService.getOrders(modifyOrderId, "");
             order = orders.get(0);

        }

        if (saveOrderId != null){
            List<OrderEntity> orders = orderService.getOrders(saveOrderId, "");
            order = orders.get(0);
            order.setOrderStatus(OrderStatus.valueOf(deliveryStatus));
            order.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
            orderService.update(order);
            request.setAttribute("saved", "true");
        }

        request.setAttribute("order", order);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/modifyOrder.jsp");
        rd.forward(request, response);

        }
        else
        {

            RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);
        }
    }
}
