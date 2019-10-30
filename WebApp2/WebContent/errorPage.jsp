<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="static util.AppConstants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());
%>
<% if(request.getAttribute(MESSAGE) != null) {%>
	<!DOCTYPE html>
		<html lang="en">
		
		<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>404 HTML Template by Colorlib</title>
		
		
		<link
			href="https://fonts.googleapis.com/css?family=Montserrat:200,400,700"
			rel="stylesheet">
		
		<link href="${pageContext.request.contextPath}/css/main.css"
			rel="stylesheet" type="text/css">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/main2.css" />
		<link href="${pageContext.request.contextPath}/css/login-style.css"
			rel="stylesheet" type="text/css">
		<script src="${pageContext.request.contextPath}/js/main.js"></script>
		<script src="${pageContext.request.contextPath}/js/main1.js"></script>
		</head>
		
		<body>
			<jsp:include page="header.jsp"></jsp:include>

			<div class="notfound">
				<div class="notfound-404">
					<h1>Oops!</h1>
				</div>
				<h2><%=request.getAttribute(MESSAGE).toString() %></h2>
				<button onclick="showError()">Get full error report</button>
				<div id="report" style="visibility: hidden"><%=request.getAttribute("FULL_MESSAGE").toString() %></div>
				<a href="login.jsp">Go To Homepage</a>
			</div>
			<jsp:include page="footer.jsp"></jsp:include>
		</body>
		
		</html>
		<% } else { %>
		<head>
			<meta http-equiv="refresh" content="0;URL=http://localhost:8080/App/login" />
		</head>
		<% }; %>

