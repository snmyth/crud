package User.web;

import com.dao.UserDAO; // Import your DAO class
import com.entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userServlet") // Define the URL pattern
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO; // Declare a UserDAO instance

    public UserServlet() {
        super();
        userDAO = new UserDAO(); // Initialize the UserDAO
    }

    // Handle GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            List<User> userList = userDAO.selectAllUser(); // Get all users
            request.setAttribute("userList", userList); // Set the user list as a request attribute
            request.getRequestDispatcher("userList.jsp").forward(request, response); // Forward to JSP page
        } else if ("view".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.selectUser(userId); // Get user by ID
            request.setAttribute("user", user);
            request.getRequestDispatcher("userView.jsp").forward(request, response); // Forward to view page
        }
    }

    // Handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            User user = new User();
            user.setName(request.getParameter("name"));
            user.setClass(request.getParameter("class"));
            user.setTeacher(Boolean.parseBoolean(request.getParameter("isTeacher")));
            user.setLevel(Integer.parseInt(request.getParameter("level")));

            try {
                userDAO.insertUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            } // Create a new user
            response.sendRedirect("userServlet?action=list"); // Redirect to list page
        } else if ("delete".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            try {
                userDAO.deleteUser(userId);
            } catch (SQLException e) {
                e.printStackTrace();
            } // Delete user by ID
            response.sendRedirect("userServlet?action=list"); // Redirect to list page
        }
    }

    
}
