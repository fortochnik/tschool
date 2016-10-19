package tstore.servlets;

import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mipan on 04.10.2016.
 */
public class BasketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        try {
            if (session.getAttribute(SessionAttributes.LOGIN).equals("true")){
                int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
//                Integer userId = Integer.valueOf(id);
                OrderEntity basket = new OrderServiceImpl().getBasketByUserId(userId);
                if (basket == null){
                    UserEntity userById = new UserServiceImpl().getUserById(Integer.valueOf(userId));
                    basket = new OrderEntity(userById);
                    new OrderServiceImpl().createOrder(basket);
                }
                request.setAttribute("basket", basket);

            }
            else{
//                todo create basket from cookies
//                response.sendRedirect("/login");
            }
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/basket.jsp");
            rd.forward(request, response);
        }
        catch (NullPointerException e)
        {

        }
    }
}
