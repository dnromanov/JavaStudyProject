package filters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Role;
import model.User;
import util.AppConstants;
import util.RoleURLMappingUtil;
import util.ServletUtil;
import static util.AppConstants.*;

/**
 * Servlet Filter implementation class CheckAuthFilter
 */
@WebFilter(filterName = "CheckAuth", urlPatterns = "/*")
public class CheckAuthFilter implements Filter {

	private Map<Role, List<String>> rolesURLsMap = null;

	/**
	 * Default constructor.
	 */
	public CheckAuthFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		User loggedUser = (User) ServletUtil.loadSessionAttribute(httpRequest, AppConstants.LOGEDIN);
		String[] excl = {"/redirect", "/errorPage.jsp"};
		List<String> excludedPaths = Arrays.asList(excl);
		
		
		
		if (excludedPaths.contains(httpRequest.getServletPath())||httpRequest.getServletPath().matches(".*(css|jpg|jpeg|png|gif|js)") ) {
			chain.doFilter(request, response);
		} else {
			// pass the request along the filter chain
			try {
				if (loggedUser != null) {
					System.out.println("User id=" + loggedUser.getUid() + "logged");
					Role userRole = loggedUser.getRole();
					List<String> allowedPaths = rolesURLsMap.get(userRole);
					if (userRole.getName().equals("SU")) {
						boolean realAdress = true;
						for (Entry<Role, List<String>> roles : rolesURLsMap.entrySet()) {
							if(!rolesURLsMap.get(roles.getKey()).contains(httpRequest.getServletPath())) {
								realAdress = false;
								break;
							}
						}
						if(realAdress) {
							httpRequest.getSession().setAttribute(AppConstants.REDIRECTED, false);
							chain.doFilter(request, response);
						}
						else {
							httpRequest.getSession().setAttribute(AppConstants.REDIRECTED, true);
							String redirectContext = (String) httpRequest.getSession()
									.getAttribute(AppConstants.URI_TO_RIDERECT_ROLE);
							httpRequest.getRequestDispatcher(redirectContext).forward(httpRequest, httpResponse);
						}
					} else if (allowedPaths.contains(httpRequest.getServletPath())) {
						httpRequest.getSession().setAttribute(AppConstants.REDIRECTED, false);
						chain.doFilter(request, response);
					}
					else {
						httpRequest.getSession().setAttribute(AppConstants.REDIRECTED, true);
						String redirectContext = (String) httpRequest.getSession()
								.getAttribute(AppConstants.URI_TO_RIDERECT_ROLE);
						httpRequest.getRequestDispatcher(redirectContext).forward(httpRequest, httpResponse);
					}

				} else {
					Role defRole = new Role(AppConstants.DEFAULT_ROLE);
					List<String> allowedPaths = rolesURLsMap.get(defRole);
					if (allowedPaths.contains(httpRequest.getServletPath())) {
						httpRequest.getSession().setAttribute(AppConstants.REDIRECTED, false);
						chain.doFilter(request, response);
					} else {
						httpRequest.getSession().setAttribute(AppConstants.REDIRECTED, true);
						String redirectContext = (String) httpRequest.getSession()
								.getAttribute(AppConstants.URI_TO_RIDERECT_ROLE);
			        	ServletUtil.setReqAttribute(httpRequest, MESSAGE, "BAD URI");
			        	ServletUtil.setReqAttribute(httpRequest, "FULL_MESSAGE", "SOME DETAILS");
			        	ServletUtil.forward(httpRequest, httpResponse, "errorPage.jsp");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute(AppConstants.MESSAGE, e.getMessage());
				httpRequest.getRequestDispatcher("errorPage.jsp").forward(httpRequest, httpResponse);
			}
		}
		

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		rolesURLsMap = RoleURLMappingUtil.getMapping();
	}

}
