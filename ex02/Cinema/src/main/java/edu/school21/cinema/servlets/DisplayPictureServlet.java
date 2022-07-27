package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@WebServlet(name = "DisplayImageServlet", urlPatterns = { "/images/*" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class DisplayPictureServlet extends HttpServlet {
    private String imagePath;

    @Override
    public void init (ServletConfig config ) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext)context.getAttribute("springContext");
        imagePath = (String) springContext.getBean("imagePath");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestURI = request.getRequestURI();
            String imageName = requestURI.substring(requestURI.lastIndexOf('/') + 1);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String user_id = String.valueOf(user.getId());
            response.setContentType("text/html");
            byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath + user_id + "/" + imageName));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            request.setAttribute("image", encodedString);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/image.jsp");
            rd.forward(request, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
