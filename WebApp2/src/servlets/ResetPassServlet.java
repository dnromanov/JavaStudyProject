package servlets;

import java.io.IOException;
import static util.AppConstants.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsersDAOImpl;
import model.User;
import util.CryptoUtils;

/**
 * Servlet implementation class ResetPassServlet
 */
@WebServlet("/resetPass")
public class ResetPassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter(TOKEN) != null) {
			String token = (String) request.getParameter(TOKEN);
			request.setAttribute("resetPass", token);
			request.getRequestDispatcher("/resetpass.jsp").forward(request, response);
			
		}
		else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter(PASS1).equals(request.getParameter(PASS1))) {
			String pass = (String) request.getParameter(PASS1);
			String token = (String) request.getParameter(TOKEN);
			User userToUpdate = new User();
			userToUpdate.setToken(token);
			new UsersDAOImpl().getUserByToken(userToUpdate);
			userToUpdate.setPassword(CryptoUtils.encrypt(pass));
			new UsersDAOImpl().updateUser(userToUpdate);
			request.setAttribute(MESSAGE, "Your pass is changed");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
