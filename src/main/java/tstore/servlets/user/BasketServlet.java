package tstore.servlets.user;

import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;
import tstore.model.enums.OrderStatus;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.ProductServiceImpl;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mipan on 04.10.2016.
 */
public class BasketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderEntity basket = null;
        HttpSession session = request.getSession(false);
        try {
            if (session.getAttribute(SessionAttributes.LOGIN).equals("true")){
                int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
//                Integer userId = Integer.valueOf(id);
                basket = new OrderServiceImpl().getBasketByUserId(userId);
                if (basket == null){
                    UserEntity userById = new UserServiceImpl().getUserById(Integer.valueOf(userId));
                    basket = new OrderEntity(userById);
                    new OrderServiceImpl().createOrder(basket);
                }


            }
            else{
//                todo create basket from cookies
//                response.sendRedirect("/login");
                basket = new OrderEntity();
                basket.setState(BasketOrderState.BASKET);
                basket.setOrderStatus(OrderStatus.DRAFT);
                Set<ProductListEntity> productListEntities = new HashSet<ProductListEntity>();
                basket.setProductList(productListEntities);
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    try {
                        int productId = Integer.parseInt(cookie.getName());
                        int count = Integer.parseInt(cookie.getValue());
                        ProductEntity product = new ProductServiceImpl().getProductById(productId);
                        ProductListEntity productListEntity = new ProductListEntity();
                        productListEntity.setCount(count);
                        productListEntity.setProduct(product);
                        basket.getProductList().add(productListEntity);

                    }
                    catch (NumberFormatException e)
                    {
                        //todo do nothing (logging exception)
                    }
                }

            }
            request.setAttribute("basket", basket);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/basket.jsp");
            rd.forward(request, response);
        }
        catch (NullPointerException e)
        {
            //todo logging exception
        }
    }
}
