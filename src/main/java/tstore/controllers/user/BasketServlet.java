package tstore.controllers.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.model.enums.BasketOrderState;
import tstore.model.enums.OrderStatus;
import tstore.service.OrderService;
import tstore.service.ProductService;
import tstore.service.UserService;
import tstore.utils.SessionAttributes;

import javax.servlet.http.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mipan on 04.10.2016.
 */
@Controller
public class BasketServlet{
    final static Logger logger = Logger.getLogger(BasketServlet.class);

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "basket", method = RequestMethod.GET)
    protected ModelAndView displayBasket(HttpServletRequest request) throws IOException {
        OrderEntity basket;
        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView("/user/basket");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
                basket = orderService.getBasketByUserId(userId);
                if (basket == null){
                    UserEntity userById = userService.getUserById(userId);
                    basket = new OrderEntity(userById);
                    orderService.createOrder(basket);
                }
            }
            else
            {
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
                        ProductEntity product = productService.getProductById(productId);
                        ProductListEntity productListEntity = new ProductListEntity();
                        productListEntity.setCount(count);
                        productListEntity.setProduct(product);
                        basket.getProductList().add(productListEntity);

                    }
                    catch (NumberFormatException e)
                    {
                        logger.info(MessageFormat.format("Wrong cookie : {0}", cookie), e);

                    }
                }
            }
            model.addObject("basket", basket);

            /*request.setAttribute("basket", basket);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/basket.jsp");
            rd.forward(request, response);*/
        }
        catch (NullPointerException e)
        {
            logger.error("Problem with basket display", e);
        }
        return model;
    }
}
