package tstore.servlets.user;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tstore.model.OrderEntity;
import tstore.model.ProductListEntity;
import tstore.service.OrderService;
import tstore.service.ProductInBasketService;
import tstore.utils.JsonParser;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Map;

/**
 * Controller updated basket after save\buy
 * Created by mipan on 17.10.2016.
 */
@Controller
public class UpdateBasket{
    final static Logger logger = Logger.getLogger(UpdateBasket.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductInBasketService productInBasketService;

    @RequestMapping(value = "updatebasket", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonOrder = request.getParameter("order");
        int totalBasket;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isLogin  = !(auth instanceof AnonymousAuthenticationToken);

//            boolean isLogin = Boolean.valueOf((String) request.getSession(false).getAttribute(SessionAttributes.LOGIN));
            Map<Integer, Integer> basketJson = null;
            basketJson = JsonParser.getBasket(jsonOrder, request);
            totalBasket = getTotalBasket(basketJson);
        /*if logged - pars and update order in db*/
            if (isLogin) {
                Integer userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());

                OrderEntity basketByUserId = updateBasket(orderService.getBasketByUserId(userId), basketJson);
            }
            else {
                Cookie[] cookies = request.getCookies();

                for (Cookie cookie : cookies) {
                    try{
                        int productId = Integer.parseInt(cookie.getName());


                        if (basketJson.get(productId)==null){
                            cookie.setMaxAge(0);
                        }
                        else {
                            cookie.setValue(String.valueOf(basketJson.get(productId)));
                        }

                        response.addCookie(cookie);
                    }
                    catch (NumberFormatException e)
                    {
                        logger.info(MessageFormat.format("Wrong cookie : {0}", cookie), e);

                    }

                }
            }
//todo update basket count label
//            todo add Spring redirect
            request.getSession(false).setAttribute(SessionAttributes.BASKET, totalBasket);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/payment.jsp");
            rd.forward(request, response);

        }
        catch (ParseException e) {
            logger.error("Error after parse cookies:", e);
            throw new IllegalArgumentException("Error after parse cookies");
        }
        catch (Exception e) {
            logger.error("Error after parse cookies:", e);
            throw new RuntimeException("Problem in update basket process", e);
        }
    }

    private int getTotalBasket(Map<Integer, Integer> basketJson) {
        int totalBasket = 0;
        for (Integer integer : basketJson.values()) {
            totalBasket+=integer;
        }
        return totalBasket;
    }

    private OrderEntity updateBasket(OrderEntity basketByUserId, Map<Integer, Integer> basketJson) {
        Iterator<ProductListEntity> iter = basketByUserId.getProductList().iterator();
        while (iter.hasNext()) {
            ProductListEntity productListEntity = iter.next();
            int productIdInOldBasket = productListEntity.getProduct().getId();
            if (basketJson.containsKey(productIdInOldBasket) && basketJson.get(productIdInOldBasket) != 0) {
                Integer count = basketJson.get(productIdInOldBasket);
                productListEntity.setCount(count);

                productInBasketService.update(productListEntity);

            } else {
                iter.remove();

                productInBasketService.delete(productListEntity);

            }
        }
        return basketByUserId;
    }

    @RequestMapping(value = "updatebasket", method = RequestMethod.GET)
    protected String doGet(){
        return "redirect:/";
    }
}
