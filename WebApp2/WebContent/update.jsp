<%@page import="model.Role"%>
<%@page import="java.util.List"%>
<%@page import="dao.RolesDAOImpl"%>
<%@page import="dao.RolesDAO"%>
<%@page import="dao.UsersDAOImpl"%>
<%@page import="dao.UsersDAO"%>
<%@page import="model.User"%>
<%@page import="util.CryptoUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="static util.AppConstants.*"%>
<%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());

	if (session.getAttribute(LOGEDIN) != null) {
		User user;
		if (session.getAttribute("editUserId") != null) {
			UsersDAO impl = new UsersDAOImpl();
			User implUser = new User();
			implUser.setUid((int)session.getAttribute("editUserId"));
			user = impl.getUserById(implUser);
		}
		else {
			user = (User) session.getAttribute(LOGEDIN);
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update</title>
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
			<%
				if (request.getAttribute(MESSAGE) != null) {
			%>
			<div><%=request.getAttribute(MESSAGE)%></div>
			<%
				}
			%>
			<form class="register-form" action="update" method="POST">
				<input type="text" value="<%=user.getName()%>" name="<%=NAME%>" /> 
				<input type="text" value="<%=CryptoUtils.decrypt(user.getPassword())%>" name="<%=PASS1%>" />
				<input type="text" value="<%=CryptoUtils.decrypt(user.getPassword())%>" name="<%=PASS2%>" />
				<input type="text" value="<%=user.getEmail()%>" name="<%=EMAIL%>" />
				<% if(((User)session.getAttribute(LOGEDIN)).getRole().getId() == 2) {
				RolesDAO impl = new RolesDAOImpl();
				List<Role> roles = impl.getAllRoles();
				%>
				<select name="rolename">
					<% for(Role role: roles) {%>
					<option value = "<%=role.getId()%>" <% if(user.getRole().getId() == role.getId()) {%>selected = "selected"<% } %>><%=role.getName()%></option>
					<%} %>
				</select>
				<% } %>
				<input type="submit" value="Update">
			</form>
			<form action="<% if(session.getAttribute("editUserId") != null) {%>admin.jsp<%} else {%>welcome<%} %>" method="GET" >
				<input type="submit" value="Cancel">
			</form>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
<%
	}
	else {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
%>