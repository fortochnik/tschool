package tstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by mipan on 08.10.2016.
 */
@Controller
public class LogoutController {
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    protected String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
