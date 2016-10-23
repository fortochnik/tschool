package tstore.servlets.filters;

import tstore.model.OrderEntity;
import tstore.model.ProductListEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * Created by mipan on 03.10.2016.
 */
public class UserIdentificationFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
            session.setAttribute(SessionAttributes.LOGIN, "false");
        }

        if (session.getAttribute(SessionAttributes.LOGIN).equals("false")) {


            Cookie[] cookies = request.getCookies();

            if (cookies == null) {
                session.setAttribute(SessionAttributes.BASKET, 0);
            } else {
                int countInBasket = 0;
                for (Cookie cookie : cookies) {
                    try {
                        int productId = Integer.parseInt(cookie.getName());
                        int count = Integer.parseInt(cookie.getValue());
                        countInBasket += count;
                    } catch (NumberFormatException e) {
                        //todo do nothing (logging exception)
                    }
                }
                session.setAttribute(SessionAttributes.BASKET, countInBasket);
            }
        } else {
            int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
            OrderEntity basketByUserId = new OrderServiceImpl().getBasketByUserId(userId);
            int totalCount = 0;
            if (basketByUserId!=null) {

                Set<ProductListEntity> productList = basketByUserId.getProductList();
                for (ProductListEntity productListEntity : productList) {
                    totalCount+=productListEntity.getCount();
                }
            }
            session.setAttribute(SessionAttributes.BASKET, totalCount);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
