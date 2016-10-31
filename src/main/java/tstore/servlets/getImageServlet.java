package tstore.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by mipan on 23.10.2016.
 */
@Controller
@RequestMapping(value="/image")
public class getImageServlet{
    @Autowired
    private ServletContext servletContext;

    final static Logger logger = Logger.getLogger(getImageServlet.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    protected byte[] doGet(@RequestParam("image") String fileName) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream("c:\\img\\"+fileName);
            BufferedImage img = ImageIO.read(is);
            bos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bos);
            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            return null; //todo: return safe photo instead
        } catch (IOException e) {
            return null;  //todo: return safe photo instead
        }
        finally {
            if (is!=null){
                is.close();
            }
            if (bos!=null){
                bos.close();
            }
        }

    }
}
