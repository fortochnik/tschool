package tstore.servlets;

import tstore.model.UserEntity;
import tstore.model.enums.Role;
import tstore.service.UserService;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mipan on 04.10.2016.
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("form-login-submit")!=null) {
            String login = request.getParameter("form-username");
            String password = request.getParameter("form-password");

            UserEntity userEntity = autorisation(login, password);
            if(userEntity != null){
                setUserDataToSession(request, userEntity);
                response.sendRedirect("/");
            }
            else
            {
                request.setAttribute("errorSignInMessage", "login or password is wrong");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        }

        if (request.getParameter("form-registration-submit")!=null){
            String email = request.getParameter("form-email");

            if (!isRegistered(email))
            {
                String name = request.getParameter("form-first-name");
                String sername = request.getParameter("form-last-name");
                String password = request.getParameter("form-password");
                UserEntity userEntity = new UserEntity(name, sername, null, email, password, Role.CLIENT);

                UserService userService = new UserServiceImpl();
                userService.createUser(userEntity);

                setUserDataToSession(request, userEntity);

                response.sendRedirect("/");
                /*RequestDispatcher rd = request.getRequestDispatcher(requestURL);
                rd.forward(request, response);*/
            }
            else
            {
                    request.getSession().invalidate();
                    request.logout();
                    request.setAttribute("errorSignUpMessage", "This email is registered.");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
            }
        }

    }

    private String getRequestURI(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        if (requestURL.equals("/login"))
        {
            requestURL = "/";
        }
        return requestURL;
    }

    private void setUserDataToSession(HttpServletRequest request, UserEntity userEntity) {
        request.getSession().setAttribute(SessionAttributes.LOGIN, "true");
        request.getSession().setAttribute(SessionAttributes.ROLE, userEntity.getRole());
        request.getSession().setAttribute(SessionAttributes.USERID, userEntity.getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }

    private UserEntity autorisation(String login, String password) {
        UserService userService = new UserServiceImpl();
        return userService.getUser(login, password);
    }
    private boolean isRegistered(String login) {
        UserService userService = new UserServiceImpl();
        if (userService.getUser(login) != null) {
            return true;
        }
        else
        {
            return false;
        }
    }


}
