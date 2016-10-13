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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        try
        {
            HttpSession session = request.getSession(false);
//            todo delete temporary part
            /*temporary*/
            session.setAttribute(SessionAttributes.LOGIN, "true");
            session.setAttribute(SessionAttributes.USERID, "1");
            /*temporary*/
            userId = Integer.valueOf((String) session.getAttribute(SessionAttributes.USERID));

            //if user login - create or update basket
            if (session.getAttribute(SessionAttributes.LOGIN).equals("true")){
                addProductToBasket(request);
            }
            else
            {
//                todo work with cookies
            }
        }
        catch (NullPointerException e){
//            todo NullPointerExc
        }

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

        if (productInBasketById == null){
            //new product in basket
            productInBasketById = new ProductListEntity(productToBasket, numberOfProduct, basket);
            productInBasketService.save(productInBasketById);
        }
        else
        {
            int number = productInBasketById.getCount() + numberOfProduct;
            productInBasketById.setCount(number);
            productInBasketService.update(productInBasketById);
        }
    }

    private int getNumberOfProduct(HttpServletRequest request) {
        return Integer.valueOf(request.getParameter("number"));
    }

    private int getProductId(HttpServletRequest request) {
        String product = request.getParameter("product");
        return Integer.valueOf(product.split("-")[1]);        
    }
}
