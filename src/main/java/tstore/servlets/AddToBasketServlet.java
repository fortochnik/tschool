package tstore.servlets;

import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.service.OrderService;
import tstore.service.ProductInBasketService;
import tstore.service.ProductService;
import tstore.service.UserService;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.ProductInBasketServiceImpl;
import tstore.service.impl.ProductServiceImpl;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by mipan on 11.10.2016.
 */
public class AddToBasketServlet extends HttpServlet {

    public static final int NUMBER_OF_JUST_ADDED = 1;
    private OrderService orderService = new OrderServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private UserService userService = new UserServiceImpl();
    private ProductInBasketService productInBasketService = new ProductInBasketServiceImpl();
    private int userId;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        try {
//
//            userId = Integer.valueOf(id);

            //if user login - create or update basket
            if (session.getAttribute(SessionAttributes.LOGIN).equals("true")) {
                userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
                addProductToBasket(request);
                response.setStatus(HttpServletResponse.SC_OK);
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
//            todo NullPointerExc
        }
        int basketCount = Integer.parseInt(session.getAttribute(SessionAttributes.BASKET).toString()) + Integer.parseInt(request.getParameter("number"));
        session.setAttribute(SessionAttributes.BASKET, basketCount);

    }

  /*  private HttpServletResponse addProductToCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        boolean newProductInBasket = true;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(request.getParameter("product"))) {
                int number = Integer.parseInt(cookie.getValue()) + Integer.parseInt(request.getParameter("number"));
                cookie.setValue(String.valueOf(number));
                newProductInBasket = false;
                break;
            }
        }
        if (newProductInBasket) {
            Cookie cookie = new Cookie(request.getParameter("product"), request.getParameter("number"));
            response.addCookie(cookie);
        }

        return response;
    }*/


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
