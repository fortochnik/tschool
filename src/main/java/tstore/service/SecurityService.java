package tstore.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
