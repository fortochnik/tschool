package tstore.utils;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import tstore.model.OrderEntity;
import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.ProductInBasketServiceImpl;
import tstore.service.impl.ProductServiceImpl;
import tstore.service.impl.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by mipan on 20.10.2016.
 */
public class UpdateBasket {
    final static Logger logger = Logger.getLogger(UpdateBasket.class);


    public static void updateBasketAfterLogin(OrderEntity basketInBD, HttpServletRequest request) throws ParseException {
        Map<Integer, Integer> orderInCookies = new HashMap<Integer, Integer>();
        Map<Integer, Integer> orderUpdated = new HashMap<Integer, Integer>();
        OrderEntity orderEntity = null;
        Cookie[] cookies = request.getCookies();


        if (basketInBD == null) {
            int userId = Integer.parseInt(request.getSession(false).getAttribute(SessionAttributes.USERID).toString());
            UserEntity userById = new UserServiceImpl().getUserById(Integer.valueOf(userId));
            basketInBD = new OrderEntity(userById);
            new OrderServiceImpl().createOrder(basketInBD);
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

    private static void updateBasketAfterLoginInDB(OrderEntity basketInDB, Map<Integer, Integer> basketInfoUpdated) {
        Set<ProductListEntity> productListInDB = basketInDB.getProductList();
        if (productListInDB == null){
            productListInDB = new HashSet<ProductListEntity>();
        }
        for (ProductListEntity productListInDBEntity : productListInDB) {
            int productId = productListInDBEntity.getProduct().getId();
            productListInDBEntity.setCount(basketInfoUpdated.get(productId));
            new ProductInBasketServiceImpl().update(productListInDBEntity);
            basketInfoUpdated.remove(productId);
        }
        for (Integer productIdFotAdd : basketInfoUpdated.keySet()) {
            ProductEntity productById = new ProductServiceImpl().getProductById(productIdFotAdd);
            ProductListEntity newProductInBasket = new ProductListEntity(productById, basketInfoUpdated.get(productIdFotAdd), basketInDB);
            productListInDB.add(newProductInBasket);
            new ProductInBasketServiceImpl().save(newProductInBasket);
        }
    }
}
