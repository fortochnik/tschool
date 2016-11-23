package tstore.utils;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by mipan on 20.10.2016.
 */
@Controller(value = "updateBasketAfterLogin")
public class UpdateBasket {
    final static Logger logger = Logger.getLogger(UpdateBasket.class);


    @Autowired
    private ProductService productService;
    @Autowired
    private ProductInBasketService productInBasketService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    /**
     * Update basket for user after login
     * add product from cookies to exists basket or create new basket
     * @param basketInBD draft user's Basket in db
     * @param request servlet request
     * @param response
     * @throws ParseException
     */
    public void updateBasketAfterLogin(OrderEntity basketInBD, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        Map<Integer, Integer> orderInCookies = new HashMap<Integer, Integer>();
        Map<Integer, Integer> orderUpdated = new HashMap<Integer, Integer>();
        OrderEntity orderEntity = null;
        Cookie[] cookies = request.getCookies();


        if (basketInBD == null) {
            int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
            UserEntity userById = userService.getUserById(Integer.valueOf(userId));
            basketInBD = new OrderEntity(userById);
            orderService.createOrder(basketInBD);
        }


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                try {
                    int productId = Integer.parseInt(cookie.getName());
                    int addCount = Integer.parseInt(cookie.getValue());
                    orderInCookies.put(productId, addCount);

                } catch (NumberFormatException e) {
                    logger.info(MessageFormat.format("Wrong cookie : {0}", cookie), e);

                }
            }

            if (orderInCookies.size() > 0) {
                Set<ProductListEntity> productListInDb = basketInBD.getProductList();
                if (productListInDb == null){
                    productListInDb = new HashSet<ProductListEntity>();
                }
                for (ProductListEntity productListEntity : productListInDb) {
                    int productId = productListEntity.getProduct().getId();
                    Integer newCount = orderInCookies.get(productId);
                    if (orderInCookies.containsKey(productId)) {
                        orderUpdated.put(productId, newCount + productListEntity.getCount());
                    } else {
                        orderUpdated.put(productId, productListEntity.getCount());
                    }
                }
                for (Integer productInCookie : orderInCookies.keySet()) {
                    if (!orderUpdated.containsKey(productInCookie))
                    {
                        orderUpdated.put(productInCookie, orderInCookies.get(productInCookie));
                    }
                }
                if (orderUpdated.size() == 0) {
                    orderUpdated = orderInCookies;
                }
                updateBasketAfterLoginInDB(basketInBD, orderUpdated);

            }
        }

    }


    private void updateBasketAfterLoginInDB(OrderEntity basketInDB, Map<Integer, Integer> basketInfoUpdated) {
        Set<ProductListEntity> productListInDB = basketInDB.getProductList();
        if (productListInDB == null){
            productListInDB = new HashSet<ProductListEntity>();
        }
        for (ProductListEntity productListInDBEntity : productListInDB) {
            int productId = productListInDBEntity.getProduct().getId();
            productListInDBEntity.setCount(basketInfoUpdated.get(productId));
            productInBasketService.update(productListInDBEntity);
            basketInfoUpdated.remove(productId);
        }
        for (Integer productIdFotAdd : basketInfoUpdated.keySet()) {
            ProductEntity productById = productService.getProductById(productIdFotAdd);
            ProductListEntity newProductInBasket = new ProductListEntity(productById, basketInfoUpdated.get(productIdFotAdd), basketInDB);
            productListInDB.add(newProductInBasket);
            productInBasketService.save(newProductInBasket);
        }
    }


}
