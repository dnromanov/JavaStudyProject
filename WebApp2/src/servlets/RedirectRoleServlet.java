package servlets;

import static util.AppConstants.REDIRECTED;
import static util.AppConstants.URI_TO_RIDERECT_ROLE;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RedirectRoleServlet
 */
@WebServlet("/redirect")
public class RedirectRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectRoleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("/redirect");
		if (request.getParameter("redirected") != null && (boolean)request.getSession().getAttribute(REDIRECTED)) {
			response.getWriter().write("invalid url registered");
		}
		else if (request.getParameter("redirect") != null) {
			response.getWriter().write("You entered a wrong or prohibited URL and were redirected to the previous visited page: " + request.getSession().getAttribute(URI_TO_RIDERECT_ROLE));
		}
		else {
			request.getRequestDispatcher((String)request.getSession().getAttribute(URI_TO_RIDERECT_ROLE)).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
