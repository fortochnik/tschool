package tstore.servlets;

import tstore.model.UserEntity;
import tstore.service.UserService;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mipan on 13.10.2016.
 */
public class UserProfileServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        UserService userService = new UserServiceImpl();

        HttpSession session = request.getSession(false);

        try {
            Integer id = Integer.valueOf((String) session.getAttribute(SessionAttributes.USERID));
            UserEntity userEntity = userService.getUserById(id);
            request.setAttribute("userdata", userEntity);
//            todo add orders to attributes
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/userprofile.jsp");
            rd.forward(request, response);
        }
        catch (NullPointerException e)
        {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("successInfoMessage", "error Info test");
        doGet(request,response);
        /*RequestDispatcher rd = request.getRequestDispatcher("/profile");
        rd.forward(request, response);*/
    }
}
