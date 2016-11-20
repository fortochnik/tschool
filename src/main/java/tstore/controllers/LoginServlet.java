package tstore.controllers;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.model.enums.Role;
import tstore.service.OrderService;
import tstore.service.UserService;
import tstore.utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidParameterException;



/**
 * Created by mipan on 04.10.2016.
 */
@Controller
public class LoginServlet{
    final static Logger logger = Logger.getLogger(LoginServlet.class);

    @Autowired
    private UpdateBasket updateBasket;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "login2", method = RequestMethod.POST)
    protected String doPost(HttpServletRequest request,
                          @RequestParam(value = "form-login-submit", required = false) String loginSubmit,
                          @RequestParam(value = "form-username", required = false) String login,
                          @RequestParam(value = "form-password", required = false) String password,
                          @RequestParam(value = "form-registration-submit", required = false) String registrationSubmit,
                          @RequestParam(value = "form-email", required = false) String email,
                          @RequestParam(value = "form-password", required = false) String newPassword,
                          @RequestParam(value = "form-first-name", required = false) String name,
                          @RequestParam(value = "form-last-name", required = false) String sername,
                          RedirectAttributes redirectAttributes
                                                    ) throws ServletException, IOException {


        try {
            if (loginSubmit != null) {
//                String login = request.getParameter("form-username");
//                String password = request.getParameter("form-password");

                UserEntity userEntity = autorisation(login, password);

                if (userEntity != null) {
                    setUserDataToSession(request, userEntity);
                    OrderEntity basket = orderService.getBasketByUserId(userEntity.getId());
                    updateBasket.updateBasketAfterLogin(basket, request);
                    return "redirect:/";
                }
                else
                {
                    redirectAttributes.addFlashAttribute("errorSignInMessage", "login or password is wrong");
                    return "redirect:login";
                    /*request.setAttribute("errorSignInMessage", "login or password is wrong");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);*/
                }
            }
            //registration
            if (registrationSubmit != null) {
//                String email = request.getParameter("form-email");
//                String password = newPassword;
                boolean possibleForRegistration = true;
                if (newPassword.length()<6){
                    possibleForRegistration = false;

                    throw new InvalidParameterException("The password less than 6 characters");
                }

                if (possibleForRegistration && !isRegistered(email)) {
//                    String name = request.getParameter("form-first-name");
//                    String sername = request.getParameter("form-last-name");

                    UserEntity userEntity = new UserEntity(name, sername, null, email, newPassword, Role.CLIENT);

//                    UserService userService = new UserServiceImpl();
                    userService.createUser(userEntity);
                    setUserDataToSession(request, userEntity);
                    OrderEntity basket = orderService.getBasketByUserId(userEntity.getId());
                    updateBasket.updateBasketAfterLogin(basket, request);
                    return "redirect:/";
                }
                else
                {
                    redirectAttributes.addFlashAttribute("errorSignUpMessage", "This email is registered.");
                    return "redirect:login";
                    /*request.setAttribute("errorSignUpMessage", "This email is registered.");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);*/
                }
            }
        } catch (ParseException e) {
            request.getSession(false).setAttribute(SessionAttributes.LOGIN, "false");
            logger.error("Invalid parameters on Login/Signin page");
            /*request.setAttribute("errorSignUpMessage", "Invalid parameters on Login/Signin page");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);*/

            redirectAttributes.addFlashAttribute("errorSignUpMessage", "Invalid parameters on Login/Signin page");
            return "redirect:login";

        }
        catch (InvalidParameterException e)
        {
            logger.error("Password must contain at least 6 characters");
            /*request.setAttribute("errorSignUpMessage", "Password must contain at least 6 characters");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);*/
            redirectAttributes.addFlashAttribute("errorSignUpMessage", "Password must contain at least 6 characters");

            return "redirect:login";
        }
        return"redirect:/login";
    }

    @RequestMapping(value = "login2", method = RequestMethod.GET)
    protected ModelAndView doGet(HttpServletRequest request) throws ServletException, IOException {
        ModelAndView login = new ModelAndView("login");

        if (request.getSession(false).getAttribute(SessionAttributes.LOGIN).equals("false")) {
            return login;
        }
        else
        {
            return new ModelAndView("redirect:/");
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

    private UserEntity autorisation(String login, String password) {
//        UserService userService = new UserServiceImpl();
        return userService.getUser(login, password);
    }

    private boolean isRegistered(String login) {
//        UserService userService = new UserServiceImpl();
        if (userService.getUser(login) != null) {
            return true;
        } else {
            return false;
        }
    }


}
