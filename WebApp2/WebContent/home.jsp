<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="static util.AppConstants.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css" />
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main2.css" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<title>Welcome</title>
</head>
<body>
	  <div class="svg-container">
    <!-- I crated SVG with: https://codepen.io/anthonydugois/pen/mewdyZ -->
    <svg viewbox="0 0 800 400" class="svg">
      <path id="curve" fill="#50c6d8" d="M 800 300 Q 400 350 0 300 L 0 0 L 800 0 L 800 300 Z">
      </path>
    </svg>
  </div>


	<jsp:include page="header.jsp"></jsp:include>

	<main>
	
		<%
		User user = (User) session.getAttribute(LOGEDIN);
		if (user != null) { %>
		<h1>Hello, <%=user.getName() %></h1>
		<a href='logout'>Logout</a>
		<% } else { %>
		<h1>Hello, stranger</h1>
		<a href='login'>Login</a>
		<% } %>


	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>