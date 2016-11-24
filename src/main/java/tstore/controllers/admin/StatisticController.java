package tstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tstore.service.ProductInBasketService;
import tstore.service.UserService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mipan on 21.10.2016.
 */
@Controller
public class StatisticController {
    @Autowired
    private UserService orderService;
    @Autowired
    private ProductInBasketService productInBasketService;

    @RequestMapping(value = "statistic", method = RequestMethod.GET)
    protected ModelAndView doGet() {
        ModelAndView model = new ModelAndView();

        List topTenProduct = productInBasketService.getTopTenProduct();
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

    }
}
