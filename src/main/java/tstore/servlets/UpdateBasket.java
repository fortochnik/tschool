package tstore.servlets;

import org.json.simple.parser.ParseException;
import tstore.model.OrderEntity;
import tstore.model.ProductListEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.ProductInBasketServiceImpl;
import tstore.utils.JsonParser;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mipan on 17.10.2016.
 */
public class UpdateBasket extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonOrder = request.getParameter("order");
        try {


        /*if logged - pars and update order in db*/
            boolean isLogin = Boolean.valueOf((String) request.getSession().getAttribute(SessionAttributes.LOGIN));
            if (isLogin) {
                Map<Integer, Integer> basketJson = null;
                basketJson = JsonParser.getBasket(jsonOrder, request);


                Integer userId = Integer.parseInt(request.getSession().getAttribute(SessionAttributes.USERID).toString());

                OrderEntity basketByUserId = updateBasket(new OrderServiceImpl().getBasketByUserId(userId), basketJson, isLogin);

//            new OrderServiceImpl().update(basketByUserId);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/payment.jsp");
                rd.forward(request, response);
            }
            else {
                //todo update cookies
            }



        }
        catch (ParseException e) {
//            todo logging and redirect to error page
        }
        catch (Exception e) {
            //todo redirect to error page
        }
    }

    private OrderEntity updateBasket(OrderEntity basketByUserId, Map<Integer, Integer> basketJson, boolean isLogin) {
        Iterator<ProductListEntity> iter = basketByUserId.getProductList().iterator();
        while (iter.hasNext()) {
            ProductListEntity productListEntity = iter.next();
            int productIdInOldBasket = productListEntity.getProduct().getId();
            if (basketJson.containsKey(productIdInOldBasket) && basketJson.get(productIdInOldBasket) != 0) {
                Integer count = basketJson.get(productIdInOldBasket);
                productListEntity.setCount(count);
                if (isLogin) {
                    new ProductInBasketServiceImpl().update(productListEntity);
                }
            } else {
                iter.remove();
                if (isLogin) {
                    new ProductInBasketServiceImpl().delete(productListEntity);
                }
            }
        }
        return basketByUserId;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
