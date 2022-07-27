package edu.school21.cinema.servlets;

import edu.school21.cinema.models.Auth;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    private UsersServiceImpl usersService;
    private String imagePath;

    @Override
    public void init (ServletConfig config ) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext)context.getAttribute("springContext");
        this.usersService = springContext.getBean(UsersServiceImpl.class);
        imagePath = (String) springContext.getBean("imagePath");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String localImagePath = imagePath;
            Optional<User> user = usersService.getUserByEmail(request.getParameter("email"), request.getParameter("password"));
            if (user.isPresent()) {
                HttpSession session = request.getSession();
                usersService.addAuth(user.get().getId(), request.getRemoteAddr());
                List<Auth> auth = usersService.getAuthList();
                session.setAttribute("auth", auth);
                session.setAttribute("user", user.get());

                localImagePath = localImagePath + user.get().getId() + "/";
                session.setAttribute("imagePath", localImagePath);
                if (!Files.isDirectory(Paths.get(localImagePath))) {
                    Files.createDirectory(Paths.get(localImagePath));
                }
                request.getRequestDispatcher("WEB-INF/jsp/profile.jsp").forward(request, response);
            } else {
                doGet(request, response);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
