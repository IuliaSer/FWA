package edu.school21.cinema.servlets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/images" })
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class FileUploadServlet extends HttpServlet {

    private String imagePath;

    @Override
    public void init (ServletConfig config ) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext)context.getAttribute("springContext");
        imagePath = (String) springContext.getBean("imagePath");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        imagePath = (String) request.getSession().getAttribute("imagePath");
        File file = new File(imagePath + fileName);
        if (!file.exists()) {
            for (Part part : request.getParts()) {
                part.write(file.getAbsolutePath());
            }
        }
        String extension = FilenameUtils.getExtension(file.getAbsolutePath());
        response.setContentType("image/" + extension);

        byte[] fileContent = FileUtils.readFileToByteArray(file);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        request.setAttribute("image", encodedString);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
        rd.forward(request, response);
    }
}