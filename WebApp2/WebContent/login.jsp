<%@page import="static util.AppConstants.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Please login</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css" />
		<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main2.css" />
	<link href="${pageContext.request.contextPath}/css/login-style.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
	<script src="${pageContext.request.contextPath}/js/main1.js"></script>
</head>
<body>
<!--	  <div class="svg-container"> -->
    <!-- I created SVG with: https://codepen.io/anthonydugois/pen/mewdyZ -->
<!--    <svg viewbox="0 0 800 400" class="svg"> -->
<!--      <path id="curve" fill="#50c6d8" d="M 800 300 Q 400 350 0 300 L 0 0 L 800 0 L 800 300 Z"> -->
<!--      </path> -->
<!--    </svg> -->
<!--  </div>  -->


	<jsp:include page="header.jsp"></jsp:include>
	<div class="login-page">
		<div class="form">
			<% if(request.getAttribute(MESSAGE) != null) {%>
			<div><%=request.getAttribute(MESSAGE) %></div>
			<%} %>
			<form class="login-form" action="login" method="POST">
				<!-- by default method = get -->
				<input type="text" placeholder="username" name="<%=NAME%>" /> <input
					type="password" placeholder="password" name="<%=PASS1%>" /> <input
					type="submit" value="LOGIN">
				<p class="message">
					Not registered? <a href="register.jsp">Create an account</a>
				</p>
			</form>
		</div>
	</div>
		<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
