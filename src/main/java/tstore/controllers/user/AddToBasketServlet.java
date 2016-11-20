package tstore.controllers.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.service.OrderService;
import tstore.service.ProductInBasketService;
import tstore.service.ProductService;
import tstore.service.UserService;
import tstore.utils.SessionAttributes;

import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by mipan on 11.10.2016.
 */
@Controller
public class AddToBasketServlet {

    final static Logger logger = Logger.getLogger(AddToBasketServlet.class);
    public static final int NUMBER_OF_JUST_ADDED = 1;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductInBasketService productInBasketService;
    private int userId;

    @RequestMapping(value = "addtobasket", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                // userDetails = auth.getPrincipal()

                userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
                addProductToBasket(request);
//                response.setStatus(HttpServletResponse.SC_OK);
            }
            else
            {
                Cookie[] cookies = request.getCookies();

                boolean newProductInBasket = true;
                String productId = String.valueOf(getProductId(request));
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(productId)) {
                        int number = Integer.parseInt(cookie.getValue()) + Integer.parseInt(request.getParameter("number"));
                        cookie.setValue(String.valueOf(number));
                        cookie.setMaxAge(60 * 60);
                        response.addCookie(cookie);
                        newProductInBasket = false;
                        break;
                    }
                }
                if (newProductInBasket) {
                    Cookie cookie = new Cookie(productId, request.getParameter("number"));
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);
                }
            }
        } catch (NullPointerException e) {
            logger.error("fail add product to basket: {0}", e);
        }
//        int basketCount = Integer.parseInt(session.getAttribute(SessionAttributes.BASKET).toString()) + Integer.parseInt(request.getParameter("number"));
//        session.setAttribute(SessionAttributes.BASKET, basketCount);

    }

    private void addProductToBasket(HttpServletRequest request) {
        int productId = getProductId(request);
        int numberOfProduct = getNumberOfProduct(request);
        ProductEntity productToBasket = productService.getProductById(productId);
        UserEntity client = userService.getUserById(userId);
        OrderEntity basket = orderService.getBasketByUserId(userId);
        //create basket if it's missing
        if (basket == null) {
            basket = new OrderEntity(client);
            orderService.createOrder(basket);
        }
        //add product to product list of basket
        ProductListEntity productInBasketById = productInBasketService.getProductInBasketById(productToBasket, basket);

        if (productInBasketById == null) {
            //new product in basket
            productInBasketById = new ProductListEntity(productToBasket, numberOfProduct, basket);
            productInBasketService.save(productInBasketById);
        } else {
            int number = productInBasketById.getCount() + numberOfProduct;
            productInBasketById.setCount(number);
            productInBasketService.update(productInBasketById);
        }
    }

    private int getNumberOfProduct(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("number"));
    }

    private int getProductId(HttpServletRequest request) {
        String product = request.getParameter("product");
        return Integer.parseInt(product.split("-")[1]);
    }
}
