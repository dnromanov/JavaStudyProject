package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
import util.CryptoUtils;
import static util.AppConstants.*;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = new User();
		String psw = request.getParameter(PASS1);
		String pswRep = request.getParameter(PASS2);
		String name = request.getParameter(NAME);
		String email = request.getParameter(EMAIL);
		String roleName = request.getParameter("rolename");
		if (psw.isEmpty() || pswRep.isEmpty() || name.isEmpty() || email.isEmpty()) {
			request.setAttribute(MESSAGE, "All fields must be filled");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		} else if (!psw.equals(pswRep)) {
			request.setAttribute(MESSAGE, "Password are not equal");
			request.getRequestDispatcher("/update.jsp").forward(request, response);
		} else {
//			user = (User) session.getAttribute(LOGEDIN);
			if (session.getAttribute("editUserId") != null) {
				UsersDAO impl = new UsersDAOImpl();
				User implUser = new User();
				implUser.setUid((int)session.getAttribute("editUserId"));
				user = impl.getUserById(implUser);
			}
			else {
				user = (User) session.getAttribute(LOGEDIN);
			}
			User registeringUser = new User();
			registeringUser.setName(name);
			registeringUser.setEmail(email);
			UsersDAOImpl impl = new UsersDAOImpl();
			if (impl.checkUserParameter(registeringUser, "NAME", user)
					|| impl.checkUserParameter(registeringUser, "EMAIL", user)) {
				request.setAttribute(MESSAGE, "A user with such name or email is already registered");
				request.getRequestDispatcher("/update.jsp").forward(request, response);
			}
			else {
				if(roleName != null) {
					RolesDAO implRole = new RolesDAOImpl();
					Role implTemp = new Role();
					implTemp.setId(Integer.parseInt(roleName));
					Role userRole = implRole.getRoleById(implTemp);
					user.setRole(userRole);
				}
				user.setEmail(email);
				user.setName(name);
				psw = CryptoUtils.encrypt(psw);
				user.setPassword(psw);
				impl.updateUser(user);
				if(((User)session.getAttribute(LOGEDIN)).getUid() == user.getUid()) {
					session.setAttribute(LOGEDIN, user);
				}
				session.removeAttribute("editUserId");
				request.getRequestDispatcher("/welcome.jsp").forward(request, response);
			}
		}
	}

}
