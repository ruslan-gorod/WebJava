
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(value = "/login")
public class MyServlet extends HttpServlet {
    List<User> users = new ArrayList<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("nameUser");
        PrintWriter out = resp.getWriter();
        User userDb = getUser(login, password);
        if (userDb.getName() != null) {
            out.println("Welcome, " + userDb.getName());
        } else if (name == null) {
            registration(req, resp);
        } else {
            if (!getUser(login, password).equals(userDb)) {
                registration(req, resp);
            } else {
                userDb.setId(users.size() + 1);
                userDb.setName(name);
                userDb.setLogin(login);
                userDb.setPassword(password);
                users.add(userDb);
                out.println("You are registered");
                out.println("Welcome, " + userDb.getName());
                for (User u : users) {
                    out.println(u);
                }
            }
        }
    }

    private User getUser(String login, String password) {
        User us = new User();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                us = user;
            }
        }
        return us;
    }

    private void registration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/registration.jsp");
        requestDispatcher.include(req, resp);
    }
}
