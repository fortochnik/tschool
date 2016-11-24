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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 13.10.2016.
 */
@Controller
public class UserProfileController {
    final static Logger logger = Logger.getLogger(UserProfileController.class);

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
                                  @RequestParam(value = "old-password", required = false) String oldPassword )  {

        profileValidator.validate(userEntity, bindingResult);
        ModelAndView model = new ModelAndView();
        if (bindingResult.hasErrors()){
            model.addObject("userdata", getUserEntityById(session));
            model.setViewName("/user/userprofile");
            return model;
        }
        try {

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
                currentUserEntity.setName(userEntity.getName());
                currentUserEntity.setSername(userEntity.getSername());
                currentUserEntity.setEmail(userEntity.getEmail());
                currentUserEntity.setBirthday(userEntity.getBirthday());
                userService.update(currentUserEntity);
                model.addObject("successInfoMessage", "Saved.");
            }

            List<OrderEntity> ordersByUser = orderService.getOrdersByUser(currentUserEntity);
            model.addObject("userdata", getUserEntityById(session));
            model.addObject("orders", ordersByUser);

            model.setViewName("/user/userprofile");
            return model;
        } catch (Exception e) {
            logger.error("Error after parse cookies:", e);
            throw new RuntimeException("Problem in update user profile", e);
        }
    }

    private UserEntity getUserEntityById(HttpSession session) {
        int userId = Integer.parseInt(session.getAttribute(SessionAttributes.USERID).toString());
        return userService.getUserById(userId);
    }
}
