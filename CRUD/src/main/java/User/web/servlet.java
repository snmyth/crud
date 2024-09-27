package User.web;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.dao.UserDAO;
import com.entity.User;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/")
public class servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public servlet() {
        this.userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
        String action = request.getServletPath();

        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "/delete":
			try {
				deleteUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            case "/edit":
               showEditForm(request, response);
                break;
            case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                break;
            default:
			try {
				listUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            	
                break;
        }
    }
    
    private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
    	List<User> listUsers = userDAO.selectAllUser();
    	request.setAttribute("ListUser", listUsers);
    	RequestDispatcher dispatcher =request.getRequestDispatcher("user-list.jsp");
    	dispatcher.forward(request, response);
    	
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }
    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String name = request.getParameter("name");
		String grade = request.getParameter("class");
		String levelParam = request.getParameter("level");
		int level;

		try {
		    level = Integer.parseInt(levelParam);
		} catch (NumberFormatException e) {
		    level = 0; // or some default value
		}
		  String isTeacher = request.getParameter("isTeacher");
		    boolean isTeacherparam = "true".equals(isTeacher);
		User newUser = new User(  name, grade, isTeacherparam, level );
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
		
	}
    private void deleteUser(HttpServletRequest request , HttpServletResponse response) throws SQLException,IOException {
    	int id = Integer.parseInt(request.getParameter("id"));
    	userDAO.deleteUser(id);
    	response.sendRedirect("list"); 	
		
	}
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	int id = Integer.parseInt(request.getParameter("id"));
    	User existingUser = userDAO.selectUser(id);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    	request.setAttribute("user", existingUser);
    	dispatcher.forward(request, response);
    	
    	
    }
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String userIdParam = request.getParameter("userId");
        String name = request.getParameter("name");
        String grade = request.getParameter("class");
        String levelParam = request.getParameter("level");
        int level = 0; // Default value

        // Validate userId, name, and grade
        if (userIdParam == null || userIdParam.isEmpty() || name == null || name.isEmpty() || grade == null || grade.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID, name, and class are required.");
            return;
        }

        

        // Parse level with error handling
        try {
            level = Integer.parseInt(levelParam);
        } catch (NumberFormatException e) {
            // Optionally log the error or handle it as needed
        }

        String isTeacherParam = request.getParameter("isTeacher");
        boolean isTeacher = "true".equals(isTeacherParam);

        // Create new User object with updated information
        User updatedUser = new User( name, grade, isTeacher, level);
        userDAO.updateUser(updatedUser); // Assuming you have an update method in userDAO

        // Redirect to list page
        response.sendRedirect("list");
    }

    
}