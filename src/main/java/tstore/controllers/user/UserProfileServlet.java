package tstore.controllers.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tstore.model.OrderEntity;
import tstore.model.UserEntity;
import tstore.service.OrderService;
import tstore.service.UserService;
import tstore.utils.SessionAttributes;
import tstore.validator.ProfileValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 13.10.2016.
 */
@Controller
public class UserProfileServlet {
    final static Logger logger = Logger.getLogger(UserProfileServlet.class);

    @Autowired
    private ProfileValidator profileValidator;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request)
            throws IOException {
        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView();


        UserEntity userEntity = getUserEntityById(session);
        List<OrderEntity> ordersByUser = orderService.getOrdersByUser(userEntity);
        model.setViewName("/user/userprofile");
        model.addObject("infoForm", new UserEntity());
        model.addObject("userdata", userEntity);
        model.addObject("orders", ordersByUser);

        return model;

    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    protected ModelAndView doPost(@ModelAttribute("infoForm") UserEntity userEntity,
                                  BindingResult bindingResult,
                                  HttpSession session,
                                  /*@RequestParam(value = "first-name") String name,
                                  @RequestParam(value = "last-name") String sername,
                                  @RequestParam(value = "email") String email,
                                  @RequestParam(value = "birthday", required = false) String birthday,*/
                                  @RequestParam(value = "old-password", required = false) String oldPassword
                                  /*@RequestParam(value = "new-password", required = false) String newPassword,
                                  @RequestParam(value = "new-password-repeat", required = false) String newPasswordRepeat*/
    ) throws ServletException, IOException {

        profileValidator.validate(userEntity, bindingResult);
        ModelAndView model = new ModelAndView();
        if (bindingResult.hasErrors()){
            model.addObject("userdata", getUserEntityById(session));
            model.setViewName("/user/userprofile");
            return model;
        }

//        HttpSession session = request.getSession(false);

        try {
//            String name = request.getParameter("first-name");
//            String sername = request.getParameter("last-name");
//            String email = request.getParameter("email");
//            String birthday = request.getParameter("birthday");
//            String oldPassword = request.getParameter("old-password");
//            String newPassword = request.getParameter("new-password");
//            String newPasswordRepeat = request.getParameter("new-password-repeat");

            UserEntity currentUserEntity = getUserEntityById(session);
            UserEntity user = userService.getUser(userEntity.getEmail());
            if (user != null && !user.equals(currentUserEntity)) {
                model.addObject("userdata", userEntity);
                model.addObject("errorInfoMessage", MessageFormat.format("The email {0} already use", userEntity.getEmail()));
                model.setViewName("/user/userprofile");
                return model;
            }


            boolean readyForSave = true;

            if (!oldPassword.equals("")) {
                if (!BCrypt.checkpw(oldPassword, currentUserEntity.getPassword())) {
                    model.addObject("passwordErrorMessage", "The current password is wrong!");
                    readyForSave = false;
                } else {
                    if (!userEntity.getPassword().equals(userEntity.getPasswordConfirm())) {
                        model.addObject("passwordErrorMessage", "The new passwords mast be identical.");
                        readyForSave = false;
                    } else {
                        currentUserEntity.setPassword(BCrypt.hashpw(userEntity.getPasswordConfirm(), BCrypt.gensalt()));
                    }
                }

            }

            if (readyForSave) {
                /*userEntity.setName(name);
                userEntity.setSername(sername);
                userEntity.setEmail(email);*/
                /*if (!birthday.equals("")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(birthday);
                    userEntity.setBirthday(date);
                }*/
//                userEntity.setId(currentUserEntity.getId());

                currentUserEntity.setName(userEntity.getName());
                currentUserEntity.setSername(userEntity.getSername());
                currentUserEntity.setEmail(userEntity.getEmail());
                currentUserEntity.setBirthday(userEntity.getBirthday());

                userService.update(currentUserEntity);
//                currentUserEntity = userEntity;
                model.addObject("successInfoMessage", "Saved.");
//                userEntity = getUserEntityById(session);
            }

            List<OrderEntity> ordersByUser = orderService.getOrdersByUser(currentUserEntity);
            model.addObject("userdata", getUserEntityById(session));
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
