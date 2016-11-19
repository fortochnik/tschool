package tstore.servlets;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.Role;
import tstore.service.OrderService;
import tstore.service.UserService;
import tstore.utils.SessionAttributes;
import tstore.utils.UpdateBasket;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by mipan on 14.11.2016.
 */
@Controller
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UpdateBasket updateBasket;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String login = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        todo get userEntity if it's CLIENT role


        request.setCharacterEncoding("UTF-8");

        if (authorities.stream().anyMatch((s)->s.getAuthority().equals(Role.ROLE_CLIENT.name()))){
            UserEntity userEntity = userService.getUser(login);

            request.getSession(false).setAttribute(SessionAttributes.ROLE, userEntity.getRole());
            request.getSession(false).setAttribute(SessionAttributes.USERID, userEntity.getId());

            OrderEntity basket = orderService.getBasketByUserId(userEntity.getId());
            try {
                updateBasket.updateBasketAfterLogin(basket, request);
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (!cookie.getName().equals("JSESSIONID")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            } catch (ParseException e) {
//                todo change logic with error
                e.printStackTrace();
            }
        }

       /* RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();*/
        response.sendRedirect(request.getContextPath() + "/");
    }
}
