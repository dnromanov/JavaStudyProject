<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="dao.UsersDAOImpl"%>
<%@ page import="model.User"%>
<%@page import="static util.AppConstants.*"%>

<%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());

	if (session.getAttribute("editUserId") != null) {
		session.removeAttribute("editUserId");
	}
	if(session.getAttribute("deleteUser") != null) {
		session.removeAttribute("deleteUser");
	}
%>
<%
	if (session.getAttribute(LOGEDIN) != null) {
		UsersDAOImpl dao = new UsersDAOImpl();
		User user = new User();
		user = (User) session.getAttribute(LOGEDIN);
		dao.getUserByName(user);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome <%=user.getName()%></title>
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main2.css" />
	<link href="${pageContext.request.contextPath}/css/login-style.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<div class="login-page">
		<div class="form">
			<h1>
				Welcome
				<%=user.getName()%></h1>
			<%
				if (request.getAttribute(MESSAGE) != null) {
			%>
			<h2><%=request.getAttribute(MESSAGE)%></h2>
			<%
				}
			%>
			<form class='login-form' action='update.jsp' method='GET'>
				<input type='submit' value='Update'>
			</form>
			<form class='login-form' action='logout' method='POST'>
				<input type='submit' value='LOGOUT'>
			</form>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
<%
	} else {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
%>