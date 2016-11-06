package tstore.servlets.admin;

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
import tstore.model.enums.Role;
import tstore.service.CategoryService;
import tstore.service.ImageService;
import tstore.service.ProductService;
import tstore.service.impl.CategoryServiceImpl;
import tstore.service.impl.ImageServiceImpl;
import tstore.service.impl.ProductServiceImpl;
import tstore.utils.SessionAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by mipan on 23.10.2016.
 */
@Controller
public class AddProductServlet {
    final static Logger logger = Logger.getLogger(AddProductServlet.class);
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    protected ModelAndView doGet(HttpSession session) {


        ModelAndView addProductPage = new ModelAndView("admin/addProduct");
//        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {

//            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            addProductPage.addObject("categories", categories);

            return addProductPage;
            /*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/addProduct.jsp");
            rd.forward(request, response);*/
        } else {
//            return new ModelAndView("forward:/");
            return new ModelAndView("redirect:/");
            /*RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);*/
        }
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
//        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {

//            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            request.setAttribute("categories", categories);

            int productId;

//            String name = request.getParameter("name");
//            String category = request.getParameter("form-category");
//            String parameters = request.getParameter("parameters");
//            String weight = request.getParameter("weight");
//            String volume = request.getParameter("volume");
//            String price = request.getParameter("price");
//            String count = request.getParameter("count");
//            String company = request.getParameter("company");

            try {
                productId = createProduct(name, category, parameters, weight, volume, price, count, company);

                /*Part mainImg = request.getPart("img1");
                Part secondImg = request.getPart("img2");
                Part thirdImg = request.getPart("img3");*/

                saveImage(productId, img1.getInputStream(), 1);
                saveImage(productId, img2.getInputStream(), 2);
                saveImage(productId, img3.getInputStream(), 3);
            } catch (NumberFormatException e) {
                logger.error(MessageFormat.format("Invalid value fore ne product : {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}", name, category, parameters, weight, volume, price, count, company), e);
                redirectAttributes.addFlashAttribute("parsErrorMessage", "Some data is invalid");
                return "redirect:add";

            } catch (IOException e) {
                logger.error("Problem with uplouded imeges", e);
                redirectAttributes.addFlashAttribute("parsErrorMessage", "Some problem with image save process. Please try again.");
                return "redirect:add";
            }

            model.addAttribute("parsSuccessMessage", "Created");

            return "admin/addProduct";
            /*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/addProduct.jsp");
            rd.forward(request, response);*/

        } else {

            return "redirect:/";
            /*RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);*/
        }
    }

    private void saveImage(int productId, InputStream fileContent, int index) throws IOException {

//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
//        InputStream fileContent = filePart.getInputStream();
        File uploads = new File("c:\\img\\");
        File file = new File(uploads, MessageFormat.format("{0}-image{1}.jpg", productId, index));
        Files.copy(fileContent, file.toPath());

//        ImageService imageService = new ImageServiceImpl();
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

//        ProductService productService = new ProductServiceImpl();
        int id = productService.save(productEntity);

        return id;
    }
}
