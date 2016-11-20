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
import tstore.model.*;
import tstore.model.enums.*;
import tstore.service.CountryService;
import tstore.service.OrderService;
import tstore.utils.SessionAttributes;
import tstore.validator.PayValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class BuyFinishServlet {
    final static Logger logger = Logger.getLogger(BuyFinishServlet.class);

    @Autowired
    private PayValidator payValidator;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "payfinish", method = RequestMethod.POST)
    protected String doPost(HttpServletRequest request, HttpServletResponse response,
                            @ModelAttribute("payForm") AddressEntity addressEntity, BindingResult bindingResult,
                          /*@RequestParam(value = "form-country") String country,
                          @RequestParam(value = "form-city") String city,
                          @RequestParam(value = "form-street") String street,
                          @RequestParam(value = "form-build") String building,
                          @RequestParam(value = "form-apartment") String apartment,
                          @RequestParam(value = "form-zipcode") String zipcode,*/
                          @RequestParam(value = "payment") String payment,
                          @RequestParam(value = "delivery") String delivery,
                            Model model
                          ) throws ServletException, IOException {

        int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
//        request.getParameterNames();
        OrderEntity basket = orderService.getBasketByUserId(userId);
            /*String country = request.getParameter("form-country");
            String city = request.getParameter("form-city");
            String street = request.getParameter("form-street");
            String building = request.getParameter("form-build");
            String apartment = request.getParameter("form-apartment");
            String zipcode = request.getParameter("form-zipcode");*/
        payValidator.validate(addressEntity, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("wrong data for buy. Validation fail.");
            List<CountryEntity> countryEntities = countryService.getAll();
            model.addAttribute("countries", countryEntities);
//            redirectAttributes.addFlashAttribute("errorSignInMessage", "Sorry, all data are necessary");
            return "user/payment";
        }


//            PaymentType payment = PaymentType.valueOf(request.getParameter("payment"));
//            DeliveryType delivery = DeliveryType.valueOf(request.getParameter("delivery"));

//            CountryEntity countryEntity = countryService.getByName(addressEntity.getCountry().getCountry());
//            AddressEntity addressEntity = new AddressEntity(countryEntity, city, zipcode, street, building, apartment);

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

            return "success";


    }

    @RequestMapping(value = "payfinish", method = RequestMethod.GET)
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "redirect:/";
    }


}
