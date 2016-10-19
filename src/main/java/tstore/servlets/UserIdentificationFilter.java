package tstore.servlets;

import tstore.utils.SessionAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by mipan on 03.10.2016.
 */
public class UserIdentificationFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        HttpSession session = request.getSession(false);
        if (session == null){
            session = request.getSession(true);
            session.setAttribute(SessionAttributes.LOGIN, "false");

//        todo delete temporary part
        /*temporary*/
//            session.setAttribute(SessionAttributes.LOGIN, "true");
//            session.setAttribute(SessionAttributes.USERID, "1");
        /*temporary*/
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
