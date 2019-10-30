package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		try {
			User user = (User) session.getAttribute(LOGEDIN);
			UsersDAOImpl impl = new UsersDAOImpl();
				if (request.getParameter("command") != null) {
					if(request.getParameter("command").equals("edit") && request.getParameter("id") != null) {
						int id = Integer.parseInt(request.getParameter("id"));
						session.setAttribute("editUserId", id);
						request.getRequestDispatcher("/update.jsp").forward(request, response);
					}
					else if(request.getParameter("command").equals("status") && request.getParameter("id") != null) {
						int id = Integer.parseInt(request.getParameter("id"));
						User userToFind = new User();
						userToFind.setUid(id);
						User userToChange = impl.getUserById(userToFind);
						boolean isUserActive;
						if(userToChange.getStatus().equals("T")) {
							isUserActive = true;
						}
						else {
							isUserActive = false;
						}
						impl.setActive(userToChange, !isUserActive);
						List<User> users = impl.getAllUsers();
						session.setAttribute(USERS_LIST, users);
						request.getRequestDispatcher("/admin.jsp").forward(request, response);
					}
					else if(request.getParameter("command").equals("resetPassword") && request.getParameter("id") != null) {
						int id = Integer.parseInt(request.getParameter("id"));
						User implUser = new User();
						implUser.setUid(id);
						User userToReset = impl.getUserById(implUser);
						request.setAttribute("user", userToReset);
						request.setAttribute("command", "resetPassword");
						request.setAttribute(MESSAGE, "Are you sure you want to reset password of user " + userToReset.getName());
						request.getRequestDispatcher("/confirm.jsp").forward(request, response);
					}
					else if(request.getParameter("command").equals("delete") && request.getParameter("id") != null) {
						int id = Integer.parseInt(request.getParameter("id"));
						User implUser = new User();
						implUser.setUid(id);
						User userToDelete = impl.getUserById(implUser);
						request.setAttribute("user", userToDelete);
						request.setAttribute("command", "deleteUser");
						request.setAttribute(MESSAGE, "Are you sure you want to delete user " + userToDelete.getName());
						request.getRequestDispatcher("/confirm.jsp").forward(request, response);
					}
					else {
						request.getRequestDispatcher("/login").forward(request, response);
					}
				}
				else if(request.getParameter("status") != null && request.getParameter("id") != null) {
					String status = request.getParameter("status");
					int id = Integer.parseInt(request.getParameter("id"));
					User userImpl = new User();
					userImpl.setUid(id);
					impl.getUserById(userImpl);
					boolean userStatus;
					if(status.equals("d")) {
						userStatus = false;
					}
					else {
						userStatus = true;
					}
					if(impl.setActive(userImpl, userStatus)) {
						impl.getUserById(userImpl);
						if(userImpl.getStatus().equals("T")) {
							response.getWriter().write("Activated");
						}
						else {
							response.getWriter().write("Deactivated");
						}
					};
				}
				else {
					List<User> users = impl.getAllUsers();
					session.setAttribute(USERS_LIST, users);
					request.getRequestDispatcher("admin.jsp").forward(request, response);
					return;
				}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			request.setAttribute("MESSAGE", e.getMessage());
			request.setAttribute("FULL_MESSAGE", errors.toString());
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(request.getParameter("id") != null && request.getParameter("command") != null) {
			String command = (String) request.getParameter("command");
			if (command.equals("deleteUser")) {
				int userId = Integer.parseInt(request.getParameter("id"));
				User delUser = new User();
				delUser.setUid(userId);
				new UsersDAOImpl().deleteUser(delUser);
				List<User> users = new UsersDAOImpl().getAllUsers();
				session.setAttribute(USERS_LIST, users);
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			}
			if (command.equals("resetPassword")) {
				int userId = Integer.parseInt(request.getParameter("id"));
				String newPass = (int)(Math.random()*10000) + "";
				String encryptedPass = CryptoUtils.encrypt(newPass);
				User resetUser = new User();
				resetUser.setUid(userId);
				new UsersDAOImpl().getUserById(resetUser);
				resetUser.setPassword(encryptedPass);
				Timestamp timestamp = new Timestamp(new Date().getTime());
				resetUser.setTokenTimestamp(timestamp);
				new UsersDAOImpl().updateUser(resetUser);
				new UsersDAOImpl().setActive(resetUser, false);
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/check?" + TOKEN + "=" + resetUser.getToken();
//				String message = "<h1>There was an attempt to reset your password. Click <a href = '"+url+"'>here</a> to update your password</h1>";
				String message = "<h1>Your pass has been resetted. Temp pass is " + newPass + ". Click <a href = '"+url+"'>here</a> to activate your account and change your password</h1>";
				AppSettings props = AppSettings.getInstance();
				MailUtils.send(props.getProperty(MAIL_USER), props.getProperty(MAIL_PASS), "dn_thief@mail.ru", "Reset pass", message);
				request.setAttribute(MESSAGE, "Mail was sent");
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			}

		}
		else {
			doGet(request, response);
		}
		
	}

}
