package tstore.servlets.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
@Controller
public class StatisticServlet{
    @Autowired
    private UserService orderService;
    @Autowired
    private ProductInBasketService productInBasketService;

    @RequestMapping(value = "statistic", method = RequestMethod.GET)
    protected ModelAndView doGet( HttpSession session) {
//        HttpSession session = request.getSession(false);
        ModelAndView model = new ModelAndView();
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {
//            ProductInBasketService productInBasketService = new ProductInBasketServiceImpl();
//            UserService orderService = new UserServiceImpl();
            List<ProductListEntity> topTenProduct = productInBasketService.getTopTenProduct();
            List topTenUser = orderService.getTopTenUser();
            BigDecimal proceedsByWeek = productInBasketService.getProceedsBy(7);
            BigDecimal proceedsByMonth = productInBasketService.getProceedsBy(30);

            proceedsByWeek.setScale(2, BigDecimal.ROUND_HALF_UP);
            proceedsByMonth.setScale(2, BigDecimal.ROUND_HALF_UP);
            model.addObject("top_products", topTenProduct);
            model.addObject("top_user", topTenUser);
            model.addObject("proceeds_week", proceedsByWeek);
            model.addObject("proceeds_month", proceedsByMonth);
            model.setViewName("admin/statistics");
            return model;
            /*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/statistics.jsp");
            rd.forward(request, response);*/
        }
        else
        {
            model.setViewName("redirect:/");
            return model;
            /*RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);*/
        }
    }
}
