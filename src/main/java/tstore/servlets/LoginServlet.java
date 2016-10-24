package tstore.servlets;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.Role;
import tstore.service.UserService;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

import static tstore.utils.UpdateBasket.updateBasketAfterLogin;

/**
 * Created by mipan on 04.10.2016.
 */
public class LoginServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if (request.getParameter("form-login-submit") != null) {
                String login = request.getParameter("form-username");
                String password = request.getParameter("form-password");

                UserEntity userEntity = autorisation(login, password);
                if (userEntity != null) {
                    setUserDataToSession(request, userEntity);
                    OrderEntity basket = new OrderServiceImpl().getBasketByUserId(userEntity.getId());
                    updateBasketAfterLogin(basket, request);
                    response.sendRedirect("/");
                } else {
                    request.setAttribute("errorSignInMessage", "login or password is wrong");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                }
            }
            //registration
            if (request.getParameter("form-registration-submit") != null) {
                String email = request.getParameter("form-email");
                String password = request.getParameter("form-password");
                boolean possibleForRegistration = true;
                if (password.length()<6){
                    possibleForRegistration = false;

                    throw new InvalidParameterException("The password less than 6 characters");
                }

                if (possibleForRegistration && !isRegistered(email)) {
                    String name = request.getParameter("form-first-name");
                    String sername = request.getParameter("form-last-name");

                    UserEntity userEntity = new UserEntity(name, sername, null, email, password, Role.CLIENT);

                    UserService userService = new UserServiceImpl();
                    userService.createUser(userEntity);
                    setUserDataToSession(request, userEntity);
                    OrderEntity basket = new OrderServiceImpl().getBasketByUserId(userEntity.getId());
                    updateBasketAfterLogin(basket, request);
                    response.sendRedirect("/");
                }
                else
                {
                    request.setAttribute("errorSignUpMessage", "This email is registered.");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                }
            }
        } catch (ParseException e) {
            request.getSession(false).setAttribute(SessionAttributes.LOGIN, "false");
            logger.error("Invalid parameters on Login/Signin page");
            request.setAttribute("errorSignUpMessage", "Invalid parameters on Login/Signin page");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);

        }
        catch (InvalidParameterException e)
        {
            logger.error("Password must contain at least 6 characters");
            request.setAttribute("errorSignUpMessage", "Password must contain at least 6 characters");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
    private String getRequestURI(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        if (requestURL.equals("/login")) {
            requestURL = "/";
        }
        return requestURL;
    }

    private void setUserDataToSession(HttpServletRequest request, UserEntity userEntity) {
        request.getSession(false).setAttribute(SessionAttributes.LOGIN, "true");
        request.getSession(false).setAttribute(SessionAttributes.ROLE, userEntity.getRole());
        request.getSession(false).setAttribute(SessionAttributes.USERID, userEntity.getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false).getAttribute(SessionAttributes.LOGIN).equals("false")) {
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
        else
        {
            response.sendRedirect("/");
        }
    }

    private UserEntity autorisation(String login, String password) {
        UserService userService = new UserServiceImpl();
        return userService.getUser(login, password);
    }

    private boolean isRegistered(String login) {
        UserService userService = new UserServiceImpl();
        if (userService.getUser(login) != null) {
            return true;
        } else {
            return false;
        }
    }


}
