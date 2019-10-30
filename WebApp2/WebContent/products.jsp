<%@page import="dao.ProductsDAO"%>
<%@page import="dao.ProductsDAOImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="static util.AppConstants.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<title>products</title>

	<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main2.css" />
	<link href="${pageContext.request.contextPath}/css/login-style.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<table>
    <thead>
      <tr>
      	<th>Product image</th>
      	<th>Product name</th>
      	<th>Type</th>
      	<th>Description</th>
      	<c:if test="${logedIn.role.id == 2}">
      	<th>Edit product</th>
      	<th>Change image</th>
      	</c:if>
      </tr>
     </thead>
     <tbody>
		<c:forEach var = "product" items = "${products}">
			<tr>
		      	<td><img src="<%=request.getContextPath()%>${product.image}" style="height:70px"></td>
		      	<td>${product.name}</td>
		      	<td>${product.type}</td>
		      	<td>${product.description}</td>
		      	<c:if test="${logedIn.role.id == 2}">
			      	<td>
			      		<form action="editproduct" method="post">
							<input hidden type="text" name="editProduct" value="${product.id}"/>
							<input type="submit" value ="Edit product"/>
						</form>
			      	</td>
			      	<td>
			      		<form action="products" method="post" enctype="multipart/form-data">
						    <input hidden type="text" name="description" value="${product.id}"/>
						    <input type="file" name="file" />
						    <input type="submit" value ="Upload file"/>
						</form>
			      	</td>
		      	
		      	</c:if>
		     </tr>

		</c:forEach>
      		<c:if test="${logedIn.role.id == 2}">
		     	<tr>
		     		<td colspan = "6" style = "text-align: center">
		     			<form action="editproduct" method="post">
							<input hidden type="text" name="addProduct"/>
							<input type="submit" value ="Add a new product"/>
						</form>
		     		</td>
		     	</tr>
		     </c:if>
     </tbody>
     </table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>