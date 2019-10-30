package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsersDAOImpl;
import model.User;
import util.AppSettings;
import util.CryptoUtils;
import static util.AppConstants.*;
/**
 * Servlet implementation class ConfirmUser
 */
@WebServlet("/check")
public class ConfirmUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConfirmUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String token = request.getParameter(TOKEN);
		if (token != null) {
			User user = new User();
			user.setToken(token);
			UsersDAOImpl impl = new UsersDAOImpl();
			if(impl.getUserByToken(user) != null) {
				long timeout = user.getTokenTimestamp().getTime() + Integer.parseInt(AppSettings.getInstance().getProperty("token_timeout_mins"))*60*1000;
				if(new Date().getTime() < timeout) {
					session.setAttribute(LOGEDIN, user);
					if(user.getStatus().equals("F")) {
						impl.setActive(user, true);
						request.setAttribute("MESSAGE", "You are registered!");
					}
				}
				else {
					impl.deleteUser(user);
					request.setAttribute(MESSAGE, "The token is invalid. Please register again");
				}
			};
		}
		RequestDispatcher rd = request.getRequestDispatcher("/welcome");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
