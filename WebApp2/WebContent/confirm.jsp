<%@page import="model.User"%>
<%@page import="dao.UsersDAOImpl"%>
<%@page import="dao.UsersDAO"%>
<%@page import="static util.AppConstants.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	session = request.getSession();
	if (request.getAttribute("user") != null) {
		User user = (User) request.getAttribute("user");
		String command = (String) request.getAttribute("command");
	%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Confirm action</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css" />
		<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main2.css" />
	<link href="${pageContext.request.contextPath}/css/login-style.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>


	<jsp:include page="header.jsp"></jsp:include>
	<div class="login-page">
		<div class="form">
			<% if(request.getAttribute(MESSAGE) != null) {%>
			<div><%=request.getAttribute(MESSAGE) %></div>
			<%} %>
			<form class="login-form" action="admin" method="POST">
				<input hidden type="text" name="id" value="${user.uid}">
				<input hidden type="text" name="command" value="${command}">
				<input type="submit" value="OK">
			</form>
			<form class="login-form" action="admin.jsp" method="GET">
				<input type="submit" value="Cancel">
			</form>
		</div>
	</div>
		<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>



	<%} else {
		RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
		rd.forward(request, response);}
%>
