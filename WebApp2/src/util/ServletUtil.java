package util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletUtil {
	public static Object loadReqParam(HttpServletRequest request, String parameter) {
		return request.getParameter(parameter);
	};
	public static void setReqAttribute(HttpServletRequest request, String parameter, Object object) {
		request.setAttribute(parameter, object);
	};
	public static Object loadReqAttribute(HttpServletRequest request, String parameter) {
		return request.getAttribute(parameter);
	};
	public static Object loadSessionAttribute(HttpServletRequest request, String parameter) {
		HttpSession session = request.getSession();
		return session.getAttribute(parameter);
	};
	public static void setSessionAttribute(HttpServletRequest request, String parameter, Object object) {
		HttpSession session = request.getSession();
		session.setAttribute(parameter, object);
	};
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
