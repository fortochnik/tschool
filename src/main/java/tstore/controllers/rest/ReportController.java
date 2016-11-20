package tstore.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tstore.model.CountryEntity;
import tstore.model.ProductEntity;
import tstore.model.UserEntity;
import tstore.service.ProductInBasketService;
import tstore.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mipan on 20.11.2016.
 */
@RestController
public class ReportController {

    @Autowired
    private UserService orderService;
    @Autowired
    private ProductInBasketService productInBasketService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public Statistic savePayment() {

        Statistic statistic = new Statistic();

        List<Object[]> topTenProduct = productInBasketService.getTopTenProduct();
        List<ProductCount> productCounts = new ArrayList<>();
        for (Object[] objects : topTenProduct) {
            ProductCount productCount = new ProductCount();
            ProductEntity productEntity = (ProductEntity) objects[0];
            Long count = (Long) objects[1];
            productCount.setCount(count);
            productCount.setId(productEntity.getId());
            productCount.setName(productEntity.getName());
            productCounts.add(productCount);
        }
        List<Object[]> topTenUser = orderService.getTopTenUser();
        List<UserOrder> userOrders = new ArrayList<>();
        for (Object[] objects : topTenUser) {
            UserOrder userOrder = new UserOrder();
            Long count = (Long) objects[1];
            UserEntity userEntity = (UserEntity) objects[0];
            userOrder.setId(userEntity.getId());
            userOrder.setLogin(userEntity.getEmail());
            userOrder.setOrdersQuantity(count);
            userOrders.add(userOrder);
        }
        BigDecimal proceedsByWeek = productInBasketService.getProceedsBy(7);
        BigDecimal proceedsByMonth = productInBasketService.getProceedsBy(30);

        statistic.setTopTenProduct(productCounts);
        statistic.setTopTenUser(userOrders);
        statistic.setProceedsByWeek(proceedsByWeek);
        statistic.setProceedsByMonth(proceedsByMonth);

        return statistic ;
    }


    private class Statistic{
        private List<ProductCount> topTenProduct;
        private List<UserOrder> topTenUser;
        private BigDecimal proceedsByWeek;
        private BigDecimal proceedsByMonth;

        public List<ProductCount> getTopTenProduct() {
            return topTenProduct;
        }

        public void setTopTenProduct(List<ProductCount> topTenProduct) {
            this.topTenProduct = topTenProduct;
        }

        public List<UserOrder> getTopTenUser() {
            return topTenUser;
        }

        public void setTopTenUser(List<UserOrder> topTenUser) {
            this.topTenUser = topTenUser;
        }

        public BigDecimal getProceedsByWeek() {
            return proceedsByWeek;
        }

        public void setProceedsByWeek(BigDecimal proceedsByWeek) {
            this.proceedsByWeek = proceedsByWeek;
        }

        public BigDecimal getProceedsByMonth() {
            return proceedsByMonth;
        }

        public void setProceedsByMonth(BigDecimal proceedsByMonth) {
            this.proceedsByMonth = proceedsByMonth;
        }
    }

    private class ProductCount {
        private int id;
        private String name;
        private Long count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }

    private class UserOrder {
        private int id;
        private String login;
        private Long ordersQuantity;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public Long getOrdersQuantity() {
            return ordersQuantity;
        }

        public void setOrdersQuantity(Long ordersQuantity) {
            this.ordersQuantity = ordersQuantity;
        }
    }
}