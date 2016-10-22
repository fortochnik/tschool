package tstore.servlets.user;

import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.service.impl.OrderServiceImpl;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by mipan on 13.10.2016.
 */
public class UserProfileServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        HttpSession session = request.getSession(false);

        if (session != null) {

                UserEntity userEntity = getUserEntityById(session);
                List<OrderEntity> ordersByUser = new OrderServiceImpl().getOrdersByUser(userEntity);
                request.setAttribute("userdata", userEntity);
                request.setAttribute("orders", ordersByUser);

//            todo add orders to attributes
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/userprofile.jsp");
                rd.forward(request, response);

        }
        else
        {
            response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        try {
            String name = request.getParameter("first-name");
            String sername = request.getParameter("last-name");
            String email = request.getParameter("email");
            String birthday = request.getParameter("birthday");
            String oldPassword = request.getParameter("old-password");
            String newPassword = request.getParameter("new-password");
            String newPasswordRepeat = request.getParameter("new-password-repeat");

            UserEntity userEntity = getUserEntityById(session);
            UserEntity user = new UserServiceImpl().getUser(email);
            if(user!=null && !user.equals(userEntity)){
                request.setAttribute("userdata", userEntity);
                request.setAttribute("errorInfoMessage", MessageFormat.format("The email {0} already use", email));
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/userprofile.jsp");
                rd.forward(request, response);
            }


            boolean readyForSave = true;

            if (oldPassword != "") {
                /*if (newPassword.length() < 6) {
                    request.setAttribute("passwordErrorMessage", "The password should be 6 or more characters!");
                    return;
                }*/



                if (!oldPassword.equals(userEntity.getPassword())) {
                    request.setAttribute("passwordErrorMessage", "The current password is wrong!");
                    readyForSave = false;
                } else {
                    if (!newPassword.equals(newPasswordRepeat) || newPassword.length() < 6) {
                        request.setAttribute("passwordErrorMessage", "The new passwords mast be identical " +
                                "\n and not less than 6 characters");
                        readyForSave = false;
                    }
                    else {
                        userEntity.setPassword(newPassword);
                    }
                }
                /*if (!newPassword.equals(newPasswordRepeat)) {
                    request.setAttribute("passwordErrorMessage", "The new passwords are not identical");
                    return;
                }*/
            }

            if (readyForSave) {
                userEntity.setName(name);
                userEntity.setSername(sername);
                userEntity.setEmail(email);
                if (birthday!="") {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(birthday);
                    userEntity.setBirthday(date);
                }
                new UserServiceImpl().update(userEntity);
                request.setAttribute("successInfoMessage", "Saved.");
                userEntity = getUserEntityById(session);
            }

            List<OrderEntity> ordersByUser = new OrderServiceImpl().getOrdersByUser(userEntity);
            request.setAttribute("userdata", userEntity);
//            ordersByUser.get(0).getAddress().
            request.setAttribute("orders", ordersByUser);


            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/userprofile.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
//            todo logging and redirect to error page
        }
    }

    private UserEntity getUserEntityById(HttpSession session) {
        int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
//        Integer userId = Integer.valueOf(id);
        return new UserServiceImpl().getUserById(userId);
    }
}
