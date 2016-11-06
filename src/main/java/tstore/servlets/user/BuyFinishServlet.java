package tstore.servlets.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tstore.model.*;
import tstore.model.enums.*;
import tstore.service.CountryService;
import tstore.service.OrderService;
import tstore.service.impl.CountryServiceImpl;
import tstore.service.impl.OrderServiceImpl;
import tstore.utils.SessionAttributes;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


/**
 * Created by mipan on 18.10.2016.
 */
@Controller
public class BuyFinishServlet {
    final static Logger logger = Logger.getLogger(BuyFinishServlet.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "payfinish", method = RequestMethod.POST)
    protected String doPost(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "form-country") String country,
                          @RequestParam(value = "form-city") String city,
                          @RequestParam(value = "form-street") String street,
                          @RequestParam(value = "form-build") String building,
                          @RequestParam(value = "form-apartment") String apartment,
                          @RequestParam(value = "form-zipcode") String zipcode,
                          @RequestParam(value = "payment") String payment,
                          @RequestParam(value = "delivery") String delivery,
                            RedirectAttributes redirectAttributes
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
        if (!country.equals("") && !city.equals("") && !street.equals("") && !building.equals("") && !apartment.equals(""))
        {
//            PaymentType payment = PaymentType.valueOf(request.getParameter("payment"));
//            DeliveryType delivery = DeliveryType.valueOf(request.getParameter("delivery"));

            CountryEntity countryEntity = countryService.getByName(country);
            AddressEntity addressEntity = new AddressEntity(countryEntity, city, zipcode, street, building, apartment);

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
        else
        {
            logger.error("wrong data for buy. Validation fail.");
            redirectAttributes.addFlashAttribute("errorSignInMessage", "Sorry, all data are necessary");
            return "redirect:/pay";
        }
    }

    @RequestMapping(value = "payfinish", method = RequestMethod.GET)
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "redirect:/";
    }


}
