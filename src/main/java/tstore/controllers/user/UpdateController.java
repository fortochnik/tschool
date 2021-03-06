package tstore.controllers.user;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Controller updated basket after save\buy
 * Created by mipan on 17.10.2016.
 */
@Controller
public class UpdateController {
    final static Logger logger = Logger.getLogger(UpdateController.class);
//    private int basketCount;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductInBasketService productInBasketService;

    @RequestMapping(value = "updatebasket", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int basketCount = 0;
        String jsonOrder = request.getParameter("order");
        int totalBasket;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isLogin  = !(auth instanceof AnonymousAuthenticationToken);

            Map<Integer, Integer> basketJson = null;
            basketJson = JsonParser.getBasket(jsonOrder, request);
        /*if logged - pars and update order in db*/
            if (isLogin) {
                Integer userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());

                updateBasket(orderService.getBasketByUserId(userId), basketJson);
                basketCount = productInBasketService.getBasketProductCount(Integer.valueOf(request.getSession().getAttribute(
                        SessionAttributes.USERID).toString()));
                request.getSession().setAttribute(SessionAttributes.BASKET, basketCount);
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

                    int countInBasket=0;
                    for (Integer integer : basketJson.values()) {
                        countInBasket+=integer;
                    }
                    Cookie countInBasketCookie = new Cookie("basket",countInBasket+"");
                    response.addCookie(countInBasketCookie);
                    basketCount = countInBasket;
                }
            }
//todo update basket count label
        }
        catch (ParseException e) {
            logger.error("Error after parse cookies:", e);
            throw new IllegalArgumentException("Error after parse cookies");
        }
        catch (Exception e) {
            logger.error("Error after parse cookies:", e);
            throw new RuntimeException("Problem in update basket process", e);
        }

        response.setContentType("text/plain");
        response.getWriter().write(basketCount+"");
    }

    @RequestMapping(value = "updatebasket", method = RequestMethod.GET)
    protected String doGet(){
        return "redirect:/";
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



}
