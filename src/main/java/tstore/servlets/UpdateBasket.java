package tstore.servlets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tstore.model.OrderEntity;
import tstore.model.ProductListEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.utils.JsonParser;
import tstore.utils.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by mipan on 17.10.2016.
 */
public class UpdateBasket extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonOrder = request.getParameter("order");
        Map<Integer, Integer> basketJson = JsonParser.getBasket(jsonOrder, request);


        Integer userId = Integer.valueOf((String) request.getSession().getAttribute(SessionAttributes.USERID));
        OrderEntity basketByUserId = new OrderServiceImpl().getBasketByUserId(userId);

        Set<ProductListEntity> productList = basketByUserId.getProductList();
        for (ProductListEntity productListEntity : productList) {
//                todo update basket send map to service;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
