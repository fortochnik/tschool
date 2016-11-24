package tstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tstore.model.OrderEntity;
import tstore.model.enums.OrderStatus;
import tstore.model.enums.PaymentStatus;
import tstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
@Controller
public class ModifyOrdersController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "modifyorder", method = RequestMethod.GET)
    protected String doGet() {
        return "redirect:/";
    }

    @RequestMapping(value = "modifyorder", method = RequestMethod.POST)
    protected String doPost(Model model,
                            @RequestParam(value = "modify", required = false) String modifyOrderId,
                            @RequestParam(value = "order-update-admin", required = false) String saveOrderId,
                            @RequestParam(value = "form-payment-status", required = false) String paymentStatus,
                            @RequestParam(value = "form-delivery-status", required = false) String deliveryStatus){

            OrderEntity order = null;
            if (modifyOrderId != null) {
                List<OrderEntity> orders = orderService.getOrders(modifyOrderId, "");
                order = orders.get(0);
            }

            if (saveOrderId != null) {
                List<OrderEntity> orders = orderService.getOrders(saveOrderId, "");
                order = orders.get(0);
                order.setOrderStatus(OrderStatus.valueOf(deliveryStatus));
                order.setPaymentStatus(PaymentStatus.valueOf(paymentStatus));
                orderService.update(order);
                model.addAttribute("saved", "true");
            }
            model.addAttribute("order", order);
            return "admin/modifyOrder";
    }
}
