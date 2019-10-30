<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="static util.AppConstants.*"%>
    <%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());
	%>
	<%if(request.getParameter(TOKEN) != null) {
		String token = (String) request.getParameter(TOKEN);
	%>
    		
    		<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reset pass</title>
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
			<form class="register-form" action="resetPass" method="POST">
				<input hidden type = "text" name = "<%=TOKEN %>" value = "<%=token%>">
 				<input
					type="password" placeholder="password" name="<%=PASS1%>" /> <input
					type="password" placeholder="retype password" name="<%=PASS2%>" />
				<input type="submit" value="Update password">
			</form>
		</div>
	</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
<% } else {
	request.getRequestDispatcher("/login.jsp").forward(request, response);
}
%>
    
    
