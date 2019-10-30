package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsersDAO;
import dao.UsersDAOImpl;
import model.User;
import util.AppSettings;
import util.CryptoUtils;
import util.MailUtils;

import static util.AppConstants.*;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean isEmailValid(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public void redirectWithMessage(String message, String adressToRedirect, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute(MESSAGE, message);
		RequestDispatcher rd = request.getRequestDispatcher(adressToRedirect);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void redirect(String adressToRedirect, HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd = request.getRequestDispatcher(adressToRedirect);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		redirect("register.jsp", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub;
		PrintWriter out = response.getWriter();
		String name = request.getParameter(NAME);
		String psw = request.getParameter(PASS1);
		String psw2 = request.getParameter(PASS2);
		String email = request.getParameter(EMAIL);

		if (name.isEmpty() || psw.isEmpty() || psw2.isEmpty() || email.isEmpty()) {
			redirectWithMessage("All fields must be filled", "register.jsp", request, response);
		} else if (!psw.equals(psw2)) {
			redirectWithMessage("Passwords are not equal", "register.jsp", request, response);
		} else if (!isEmailValid(email)) {
			redirectWithMessage("Email is wrong", "register.jsp", request, response);
		} else {
			User user = new User();
			user.setName(name);
			psw = CryptoUtils.encrypt(psw);
			user.setPassword(psw);
			user.setEmail(email);
			UsersDAO impl = new UsersDAOImpl();
			if (impl.getUserByName(user) != null) {
				redirectWithMessage("The name is already used", "register.jsp", request, response);
			} else {
				try {
					String token = CryptoUtils.encrypt(Integer.toString((int) (Math.random() * 1000000)));
					user.setToken(token);
					impl.createUser(user);
					String url = request.getRequestURL().toString();
					url = url.substring(0, url.lastIndexOf("/")) + "/check?" + TOKEN + "=" + token;

					String message = "<h1><a href = '" + url + "'>Click here to submit yout account</a></h1>";
					AppSettings props = AppSettings.getInstance();
					MailUtils.send(props.getProperty(MAIL_USER), props.getProperty(MAIL_PASS), email,
							"Servlet App - > Registration info", message);
					request.setAttribute(MESSAGE, new Date() + " -> Check your email and then enter your reg data");
				} catch (Exception e) {
					e.printStackTrace();
				}
				redirect("login.jsp", request, response);
			}

		}
	}

}
