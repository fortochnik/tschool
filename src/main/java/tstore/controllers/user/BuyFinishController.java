package tstore.controllers.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tstore.model.AddressEntity;
import tstore.model.CountryEntity;
import tstore.model.OrderEntity;
import tstore.model.enums.*;
import tstore.service.CountryService;
import tstore.service.OrderService;
import tstore.utils.SessionAttributes;
import tstore.validator.PayValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * Created by mipan on 18.10.2016.
 */
@Controller
public class BuyFinishController {
    final static Logger logger = Logger.getLogger(BuyFinishController.class);

    @Autowired
    private PayValidator payValidator;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "payfinish", method = RequestMethod.POST)
    protected String doPost(HttpServletRequest request,
                            @ModelAttribute("payForm") AddressEntity addressEntity, BindingResult bindingResult,
                            @RequestParam(value = "payment") String payment,
                            @RequestParam(value = "delivery") String delivery,
                            Model model
    ) throws ServletException, IOException {

        int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
        OrderEntity basket = orderService.getBasketByUserId(userId);

        payValidator.validate(addressEntity, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("wrong data for buy. Validation fail.");
            List<CountryEntity> countryEntities = countryService.getAll();
            model.addAttribute("countries", countryEntities);
            return "user/payment";
        }

        addressEntity.setCountry(countryService.getByName(addressEntity.getCountry().getCountry()));
        basket.setAddress(addressEntity);
        basket.setDeliveryType(DeliveryType.valueOf(delivery));
        basket.setPaymentType(PaymentType.valueOf(payment));
        basket.setPaymentStatus(PaymentStatus.NOT_PAID);
        basket.setOrderStatus(OrderStatus.PLACED);
        basket.setState(BasketOrderState.ORDER);

        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date orderDate = Date.from(instant);
        basket.setOrderDate(orderDate);

        orderService.updateBasketToOrder(basket);
        request.getSession().setAttribute(SessionAttributes.BASKET, 0);
        return "success";
    }

    @RequestMapping(value = "payfinish", method = RequestMethod.GET)
    protected String doGet() {
        return "redirect:/";
    }


}
