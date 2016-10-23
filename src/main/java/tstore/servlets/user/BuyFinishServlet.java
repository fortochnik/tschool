package tstore.servlets.user;

import tstore.model.*;
import tstore.model.enums.*;
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
public class BuyFinishServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
        request.getParameterNames();
        OrderEntity basket = new OrderServiceImpl().getBasketByUserId(userId);
            String country = request.getParameter("form-country");
            String city = request.getParameter("form-city");
            String street = request.getParameter("form-street");
            String building = request.getParameter("form-build");
            String apartment = request.getParameter("form-apartment");
            String zipcode = request.getParameter("form-zipcode");
        if (!country.equals("") && !city.equals("") && !street.equals("") && !building.equals("") && !apartment.equals(""))
        {
            PaymentType payment = PaymentType.valueOf(request.getParameter("payment"));
            DeliveryType delivery = DeliveryType.valueOf(request.getParameter("delivery"));

            CountryEntity countryEntity = new CountryServiceImpl().getByName(country);
            AddressEntity addressEntity = new AddressEntity(countryEntity, city, zipcode, street, building, apartment);

            basket.setAddress(addressEntity);
            basket.setDeliveryType(delivery);
            basket.setPaymentType(payment);
            basket.setPaymentStatus(PaymentStatus.NOT_PAID);
            basket.setOrderStatus(OrderStatus.PLACED);
            basket.setState(BasketOrderState.ORDER);

            LocalDate localDate = LocalDate.now();
            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date orderDate = Date.from(instant);
            basket.setOrderDate(orderDate);

            new OrderServiceImpl().updateBasketToOrder(basket);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/success.jsp");
            rd.forward(request, response);
        }
        else
        {
//            todo logging Error and redirect to exception
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/");
    }


}
