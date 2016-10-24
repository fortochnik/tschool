package tstore.servlets.admin;

import tstore.model.ProductEntity;
import tstore.model.ProductListEntity;
import tstore.model.UserEntity;
import tstore.model.enums.Role;
import tstore.service.ProductInBasketService;
import tstore.service.UserService;
import tstore.service.impl.ProductInBasketServiceImpl;
import tstore.service.impl.UserServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mipan on 21.10.2016.
 */
public class StatisticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {
            ProductInBasketService productInBasketService = new ProductInBasketServiceImpl();
            UserService orderService = new UserServiceImpl();
            List<ProductListEntity> topTenProduct = productInBasketService.getTopTenProduct();
            List topTenUser = orderService.getTopTenUser();
            BigDecimal proceedsByWeek = productInBasketService.getProceedsBy(7);
            BigDecimal proceedsByMonth = productInBasketService.getProceedsBy(30);

            proceedsByWeek.setScale(2, BigDecimal.ROUND_HALF_UP);
            proceedsByMonth.setScale(2, BigDecimal.ROUND_HALF_UP);
            request.setAttribute("top_products", topTenProduct);
            request.setAttribute("top_user", topTenUser);
            request.setAttribute("proceeds_week", proceedsByWeek);
            request.setAttribute("proceeds_month", proceedsByMonth);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/statistics.jsp");
            rd.forward(request, response);
        } else {

            RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);
        }
    }
}
