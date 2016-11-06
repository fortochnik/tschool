package tstore.servlets.product;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tstore.exceptions.PageNotFoundException;
import tstore.model.ProductEntity;
import tstore.service.ProductService;
import tstore.service.impl.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by mipan on 25.09.2016.
 */
@Controller
public class ProductServlet {
    final static Logger logger = Logger.getLogger(ProductServlet.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "product/{id}", method = RequestMethod.GET)
    public ModelAndView doGet(HttpServletRequest request, @PathVariable(value = "id") String id)
            throws IOException {
        String requestURI = request.getRequestURI();
        ModelAndView modelAndView = new ModelAndView("/product/product");


        int productId = getProductIdFromUri(id);
        ProductEntity product = productService.getProductById(productId);
        if (product == null){
            logger.info(MessageFormat.format("Product not found by id: {0}", productId));
            throw new PageNotFoundException();
        }

        return modelAndView.addObject("product", product);
/*
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/product/product.jsp");
        rd.forward(request, response);*/
    }

    @RequestMapping(value = "product/*", method = RequestMethod.POST)
    public String doPost() {
        return "redirect:/";
    }

    private int getProductIdFromUri(String id) {
        int productId;
        /*String[] split = requestURI.split("/");*/
        try {
            productId = Integer.parseInt(id);
        }
        catch (NumberFormatException e)
        {
            logger.info(MessageFormat.format("Product not found by id: {0}", id));
            throw new PageNotFoundException(e.getMessage());
        }
        return productId;
    }
}
