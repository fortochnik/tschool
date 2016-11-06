package tstore.servlets.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tstore.model.CountryEntity;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.service.CountryService;
import tstore.service.OrderService;
import tstore.service.ProductService;
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
@Controller
public class BuyServlet{

    @Autowired
    private OrderService orderService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "pay", method = RequestMethod.POST)
    protected ModelAndView doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ModelAndView model = new ModelAndView();

        int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
//        request.getParameterNames();
        OrderEntity basket = orderService.getBasketByUserId(userId);

        List<String> deficiency = productNumberValidation(basket.getProductList());


        if (basket.getProductList().size()!=0 && deficiency.size()==0) {
            List<CountryEntity> countryEntities = countryService.getAll();
            model.addObject("countries", countryEntities);
            model.setViewName("user/payment");
            return model;
        }
        else
        {
            if (basket.getProductList().size()==0){
                deficiency.add("You try buy the empty basket");
            }
            model.addObject("basket", basket);
            model.addObject("deficiency", deficiency);
            model.setViewName("user/basket");
            return model;
        }
    }

    @RequestMapping(value = "pay", method = RequestMethod.GET)
    protected String doGet() {
        return "redirect:/";
    }

    private List<String> productNumberValidation(Set<ProductListEntity> productList) {
        List<String> deficiency = new ArrayList<String>();
        for (ProductListEntity basketProductNote : productList) {
            int orderNumber = basketProductNote.getCount();
            ProductEntity productInStock = productService.getProductById(basketProductNote.getProduct().getId());
            if (orderNumber > productInStock.getCount())
            {
                deficiency.add(MessageFormat.format("Sorry, but we have only {0} \"{1}\"", productInStock.getCount(), productInStock.getName()));
            }
        }
        return deficiency;
    }

}
