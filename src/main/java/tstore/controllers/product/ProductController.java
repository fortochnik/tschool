package tstore.controllers.product;

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

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by mipan on 25.09.2016.
 */
@Controller
public class ProductController {
    final static Logger logger = Logger.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "product/{id}", method = RequestMethod.GET)
    public ModelAndView doGet(@PathVariable(value = "id") String id)
            throws IOException {
        ModelAndView modelAndView = new ModelAndView("product/product");
        int productId = getProductIdFromUri(id);
        ProductEntity product = productService.getProductById(productId);
        if (product == null){
            logger.info(MessageFormat.format("Product not found by id: {0}", productId));
            throw new PageNotFoundException();
        }

        return modelAndView.addObject("product", product);
    }

    @RequestMapping(value = "product/*", method = RequestMethod.POST)
    public String doPost() {
        return "redirect:/";
    }

    private int getProductIdFromUri(String id) {
        int productId;
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
