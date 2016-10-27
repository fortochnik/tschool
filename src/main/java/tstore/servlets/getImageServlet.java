package tstore.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by mipan on 23.10.2016.
 */
public class getImageServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(getImageServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream output = null;
        try{

            String fileName = request.getParameter("image");
            FileInputStream fis = new FileInputStream(new File("c:\\img\\"+fileName));
            bufferedInputStream = new BufferedInputStream(fis);

            response.setContentType("image/jpeg");
            output = new BufferedOutputStream(response.getOutputStream());
            for (int data; (data = bufferedInputStream.read()) > -1;) {
                output.write(data);
            }
        }
        catch(IOException e)
        {
            logger.error("Error on read from file system image process:", e);

        }finally{
            if (bufferedInputStream!=null)
            {
                bufferedInputStream.close();
            }
            if (output!=null)
            {
                output.close();
            }
        }
    }
}
