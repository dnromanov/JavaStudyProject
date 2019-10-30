package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RolesDAO;
import dao.RolesDAOImpl;
import dao.UsersDAO;
import dao.UsersDAOImpl;
import model.Role;
import model.User;
import util.AppSettings;
import util.CryptoUtils;
import static util.AppConstants.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute(LOGEDIN) != null) {
			RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			User user = new User();
			String name = request.getParameter(NAME);
			String psw = request.getParameter(PASS1);
			if (!name.isEmpty() && !psw.isEmpty()) {
				user.setName(name);
				psw = CryptoUtils.encrypt(psw);
				user.setPassword(psw);
				UsersDAO dao = new UsersDAOImpl();
				// DB check
				if (dao.getUserByName(user) == null) {
					request.setAttribute(MESSAGE, "Sorry. Such user is not registered");
				} else if (!dao.getUserByName(user).getPassword().equals(psw)) {
					request.setAttribute(MESSAGE, "Sorry. Password is wrong");
				} else if (!dao.getUserByName(user).getStatus().equals("T")) {
					request.setAttribute(MESSAGE, "Sorry. User is not confirmed");
				} else {
					HttpSession session = request.getSession();
					session.setAttribute(LOGEDIN, user);
					int sessionInterval = Integer.parseInt(AppSettings.getInstance().getProperty("session_interval"));
					;
					session.setMaxInactiveInterval(sessionInterval);
				}
			} else {
				request.setAttribute(MESSAGE, "Username and Password can not be empty");
			}
			RequestDispatcher rd = request.getRequestDispatcher("/welcome");
			rd.forward(request, response);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			request.setAttribute(MESSAGE, "Some error occured");
			request.setAttribute("FULL_MESSAGE", errors.toString());
			request.getRequestDispatcher("errorPage.jsp").forward(request, response);
			return;
		}
	}

}
