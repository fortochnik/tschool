package tstore.servlets.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class AdminOrderListServlet {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "orders", method = RequestMethod.POST)
    protected String doPost(Model model, HttpSession session,
                            @RequestParam(value = "is-custom-search", required = false) String customSearch,
                            @RequestParam(value = "order-number", required = false) String orderNumber,
                            @RequestParam(value = "email", required = false) String userEmail,
                            @RequestParam(value = "all-not-delivered", required = false) String allNotDelivered,
                            @RequestParam(value = "all-not-paid", required = false) String allNotPaid,
                            @RequestParam(value = "orders-all", required = false) String allOrders,
                            HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(false);

        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {

            List<OrderEntity> orderEntityList = null;
            if (customSearch != null) {
//            String orderNumber = request.getParameter("order-number");
//            String userEmail = request.getParameter("email");
                orderEntityList = getCustomSearchResult(orderNumber, userEmail);
            }

            if (allNotDelivered != null) {
                orderEntityList = orderService.getNotDelivered();
            }

            if (allNotPaid != null) {
                orderEntityList = orderService.getNotPaid();
            }

            if (allOrders != null) {
                orderEntityList = orderService.getAllOrders();
            }


            model.addAttribute("orders", orderEntityList);
            return "admin/orderslist";
        /*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/orderslist.jsp");
        rd.forward(request, response);*/


        } else {

            return "redirect:/";
        }
    }

    private List<OrderEntity> getCustomSearchResult(String orderNumber, String userEmail) {

        List<OrderEntity> orderEntities = orderService.getOrders(orderNumber, userEmail);
        return orderEntities;
    }

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    protected String doGet(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(false);

        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") && (session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN)
                || session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE))) {
            return "admin/orderslist";
        } else {
            return "redirect:login";
        }


    }
}
