<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="static util.AppConstants.*"%>
<%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>

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
			<form class="register-form" action="register" method="POST">
				<input type="text" placeholder="name" name="<%=NAME%>" /> <input
					type="password" placeholder="password" name="<%=PASS1%>" /> <input
					type="password" placeholder="retype password" name="<%=PASS2%>" />
				<input type="text" placeholder="email address" name="<%=EMAIL%>" />
				<input type="submit" value="Register">
				<p class="message">
					Already registered? <a href="login.jsp">Sign In</a>
				</p>
			</form>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>