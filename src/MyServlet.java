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
        User userDb = new User();
        if (name == null) {
            //users.add(new User("Ruslan", "ruslan-gorod", "123456"));
            for (User user : users) {
                if (user.getLogin().equals(login) & user.getPassword().equals(password)) {
                    userDb = user;
                }
            }
            if (userDb.getName() != null) {
                out.println("Welcome, " + userDb.getName());
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/registration.jsp");
                requestDispatcher.include(req, resp);
            }
        } else {
            userDb = new User(name, login, password);
            if (!users.contains(userDb)) {
                users.add(userDb);
            }
            out.println("Welcome, " + userDb.getName());
            out.println("Count users in system - " + users.size());
        }
    }

}
