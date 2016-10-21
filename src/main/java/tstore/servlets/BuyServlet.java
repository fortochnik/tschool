package tstore.servlets;

import tstore.model.CountryEntity;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.service.impl.CountryServiceImpl;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.ProductServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 18.10.2016.
 */
public class BuyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
        request.getParameterNames();
        OrderEntity basket = new OrderServiceImpl().getBasketByUserId(userId);

        List<String> deficiency = productNumberValidation(basket.getProductList());


        if (basket.getProductList().size()!=0 && deficiency.size()==0) {
            List<CountryEntity> countryEntities = new CountryServiceImpl().getAll();
            request.setAttribute("countries", countryEntities);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/payment.jsp");
            rd.forward(request, response);
        }
        else
        {
            if (basket.getProductList().size()==0){
                deficiency.add("You try buy the empty basket");
            }
            request.setAttribute("basket", basket);
            request.setAttribute("deficiency", deficiency);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/basket.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/");
    }

    private List<String> productNumberValidation(Set<ProductListEntity> productList) {
        List<String> deficiency = new ArrayList<String>();
        for (ProductListEntity basketProductNote : productList) {
            int orderNumber = basketProductNote.getCount();
            ProductEntity productInStock = new ProductServiceImpl().getProductById(basketProductNote.getProduct().getId());
            if (orderNumber > productInStock.getCount())
            {
                deficiency.add(MessageFormat.format("Sorry, but we have only {0} \"{1}\"", productInStock.getCount(), productInStock.getName()));
            }
        }
        return deficiency;
    }

}
