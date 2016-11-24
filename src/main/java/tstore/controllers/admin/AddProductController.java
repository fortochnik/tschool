package tstore.controllers.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tstore.model.CategoryEntity;
import tstore.model.ProductEntity;
import tstore.service.CategoryService;
import tstore.service.ImageService;
import tstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
@Controller
public class AddProductController {
    final static Logger logger = Logger.getLogger(AddProductController.class);
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    protected ModelAndView doGet(HttpSession session) {
        ModelAndView addProductPage = new ModelAndView("admin/addProduct");

        List<CategoryEntity> categories = categoryService.getCategories();
        addProductPage.addObject("categories", categories);

        return addProductPage;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    protected String doPost(Model model,
                            HttpSession session,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "form-category", required = false) String category,
                            @RequestParam(value = "parameters", required = false) String parameters,
                            @RequestParam(value = "weight", required = false) String weight,
                            @RequestParam(value = "volume", required = false) String volume,
                            @RequestParam(value = "price", required = false) String price,
                            @RequestParam(value = "count", required = false) String count,
                            @RequestParam(value = "company", required = false) String company,
                            @RequestParam("img1") MultipartFile img1,
                            @RequestParam("img2") MultipartFile img2,
                            @RequestParam("img3") MultipartFile img3,

                            @RequestParam(value = "test", required = false) String test,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest request, HttpServletResponse response) throws ServletException {

        List<CategoryEntity> categories = categoryService.getCategories();
        request.setAttribute("categories", categories);

        int productId;

        try {
            productId = createProduct(name, category, parameters, weight, volume, price, count, company);
            saveImage(productId, img1.getInputStream(), 1);
            saveImage(productId, img2.getInputStream(), 2);
            saveImage(productId, img3.getInputStream(), 3);
        } catch (NumberFormatException e) {
            logger.error(MessageFormat.format("Invalid value fore ne product : {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}",
                    name, category, parameters, weight, volume, price, count, company), e);
            redirectAttributes.addFlashAttribute("parsErrorMessage", "Some data is invalid");
            return "redirect:add";

        } catch (IOException e) {
            logger.error("Problem with uplouded imeges", e);
            redirectAttributes.addFlashAttribute("parsErrorMessage", "Some problem with image save process. Please try again.");
            return "redirect:add";
        }

        model.addAttribute("parsSuccessMessage", "Created");

        return "admin/addProduct";
    }

    private void saveImage(int productId, InputStream fileContent, int index) throws IOException {
        File uploads = new File("c:\\img\\");
        File file = new File(uploads, MessageFormat.format("{0}-image{1}.jpg", productId, index));
        Files.copy(fileContent, file.toPath());
        imageService.save(MessageFormat.format("{0}-image{1}.jpg", productId, index), productId);

    }

    private int createProduct(String name, String category, String parameters,
                              String weight, String volume, String price, String count, String company) throws NumberFormatException {
        double weightPars = Double.parseDouble(weight);
        double volumePars = Double.parseDouble(volume);
        BigDecimal pricePars = BigDecimal.valueOf(Double.parseDouble(price));
        int countPars = Integer.parseInt(count);
        int categoryId = Integer.parseInt(category);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setParameters(parameters);
        productEntity.setWeight(weightPars);
        productEntity.setVolume(volumePars);
        productEntity.setPrice(pricePars);
        productEntity.setCount(countPars);
        productEntity.setCompany(company);
        CategoryEntity categoryEntity = categoryService.get(categoryId);
        productEntity.setCategory(categoryEntity);
        int id = productService.save(productEntity);

        return id;
    }
}
