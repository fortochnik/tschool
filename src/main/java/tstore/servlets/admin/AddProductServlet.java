package tstore.servlets.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
public class AddProductServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(AddProductServlet.class);
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    protected ModelAndView doGet(HttpSession session) throws ServletException, IOException {


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

    @Override
    @RequestMapping(value = "add", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionAttributes.LOGIN).equals("true") &&
                (session.getAttribute(SessionAttributes.ROLE).equals(Role.EMPLOYEE) ||
                        session.getAttribute(SessionAttributes.ROLE).equals(Role.ADMIN))) {

//            CategoryService categoryService = new CategoryServiceImpl();
            List<CategoryEntity> categories = categoryService.getCategories();
            request.setAttribute("categories", categories);

            int productId;

            String name = request.getParameter("name");
            String category = request.getParameter("form-category");
            String parameters = request.getParameter("parameters");
            String weight = request.getParameter("weight");
            String volume = request.getParameter("volume");
            String price = request.getParameter("price");
            String count = request.getParameter("count");
            String company = request.getParameter("company");

            try {
                productId = createProduct(name, category, parameters, weight, volume, price, count, company);

                Part mainImg = request.getPart("img1");
                Part secodImg = request.getPart("img2");
                Part thirdImg = request.getPart("img3");

                saveImage(productId, mainImg, 1);
                saveImage(productId, secodImg, 2);
                saveImage(productId, thirdImg, 3);
            } catch (NumberFormatException e) {
                logger.error(MessageFormat.format("Invalid value fore ne product : {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}", name, category, parameters, weight, volume, price, count, company), e);
                request.setAttribute("parsErrorMessage", "Some data is invalid");

            } catch (IOException e) {
                logger.error("Problem with uplouded imeges", e);
                request.setAttribute("parsErrorMessage", "Some problem with image save process. Please try again.");

            }

            request.setAttribute("parsSuccessMessage", "Created");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/addProduct.jsp");
            rd.forward(request, response);

        } else {

            RequestDispatcher rd = request.getRequestDispatcher("/");
            rd.forward(request, response);
        }
    }

    private void saveImage(int productId, Part filePart, int index) throws IOException {

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        File uploads = new File("c:\\img\\");
        File file = new File(uploads, MessageFormat.format("{0}-image{1}.jpg", productId, index));
        Files.copy(fileContent, file.toPath());

        ImageService imageService = new ImageServiceImpl();
        imageService.save(MessageFormat.format("{0}-image{1}.jpg", productId, index), productId);

    }

    private int createProduct(String name, String category, String parameters, String weight, String volume, String price, String count, String company) throws NumberFormatException {
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
        CategoryEntity categoryEntity = new CategoryServiceImpl().get(categoryId);
        productEntity.setCategory(categoryEntity);

        ProductService productService = new ProductServiceImpl();
        int id = productService.save(productEntity);

        return id;
    }
}
