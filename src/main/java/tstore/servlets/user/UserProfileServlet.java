package tstore.servlets.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.service.OrderService;
import tstore.service.UserService;
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
@Controller
public class UserProfileServlet {
    final static Logger logger = Logger.getLogger(UserProfileServlet.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request)
            throws IOException {
        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView();
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true")) {

                UserEntity userEntity = getUserEntityById(session);
                List<OrderEntity> ordersByUser = orderService.getOrdersByUser(userEntity);
                model.setViewName("/user/userprofile");
                model.addObject("userdata", userEntity);
                model.addObject("orders", ordersByUser);

                return model;
        }
        else
        {
            model.setViewName("redirect:/");
            return model;
        }
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    protected ModelAndView doPost(HttpSession session, @RequestParam(value = "first-name") String name,
                                  @RequestParam(value = "last-name") String sername,
                                  @RequestParam(value = "email") String email,
                                  @RequestParam(value = "birthday", required = false) String birthday,
                                  @RequestParam(value = "old-password", required = false) String oldPassword,
                                  @RequestParam(value = "new-password", required = false) String newPassword,
                                  @RequestParam(value = "new-password-repeat", required = false) String newPasswordRepeat
                                  ) throws ServletException, IOException {

        ModelAndView model = new ModelAndView();
//        HttpSession session = request.getSession(false);

        try {
//            String name = request.getParameter("first-name");
//            String sername = request.getParameter("last-name");
//            String email = request.getParameter("email");
//            String birthday = request.getParameter("birthday");
//            String oldPassword = request.getParameter("old-password");
//            String newPassword = request.getParameter("new-password");
//            String newPasswordRepeat = request.getParameter("new-password-repeat");

            UserEntity userEntity = getUserEntityById(session);
            UserEntity user = userService.getUser(email);
            if(user!=null && !user.equals(userEntity)){
                model.addObject("userdata", userEntity);
                model.addObject("errorInfoMessage", MessageFormat.format("The email {0} already use", email));
                model.setViewName("/user/userprofile");
                return model;
            }


            boolean readyForSave = true;

            if (!oldPassword.equals("")) {
                if (!oldPassword.equals(userEntity.getPassword())) {
                    model.addObject("passwordErrorMessage", "The current password is wrong!");
                    readyForSave = false;
                } else {
                    if (!newPassword.equals(newPasswordRepeat) || newPassword.length() < 6) {
                        model.addObject("passwordErrorMessage", "The new passwords mast be identical " +
                                "\n and not less than 6 characters");
                        readyForSave = false;
                    }
                    else {
                        userEntity.setPassword(newPassword);
                    }
                }

            }

            if (readyForSave) {
                userEntity.setName(name);
                userEntity.setSername(sername);
                userEntity.setEmail(email);
                if (!birthday.equals("")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(birthday);
                    userEntity.setBirthday(date);
                }
                userService.update(userEntity);
                model.addObject("successInfoMessage", "Saved.");
                userEntity = getUserEntityById(session);
            }

            List<OrderEntity> ordersByUser = orderService.getOrdersByUser(userEntity);
            model.addObject("userdata", userEntity);
            model.addObject("orders", ordersByUser);

            model.setViewName("/user/userprofile");
            return model;
            /*RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/user/userprofile.jsp");
            rd.forward(request, response);*/
        } catch (Exception e) {
            logger.error("Error after parse cookies:", e);
            throw new RuntimeException("Problem in update user profile", e);
        }
//        return model;
    }

    private UserEntity getUserEntityById(HttpSession session) {
        int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
        return userService.getUserById(userId);
    }
}
