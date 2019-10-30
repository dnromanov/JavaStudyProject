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
	request.setAttribute("ACTION", request.getServletPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit products</title>
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
			<c:if test="${message ne null}">
				<div>${message}</div>
			</c:if>
			<div>${EDITED_PRODUCT eq null? "Add a new product" : EDITED_PRODUCT.name}</div>
			<form class="register-form" action="editproduct" method="POST" enctype="multipart/form-data">
				<input hidden type="text" value="${EDITED_PRODUCT eq null?"adding" : "edition"}" name="action" />
				<c:if test="${EDITED_PRODUCT ne null}">
					<input hidden type="text" value="${EDITED_PRODUCT.id}" name="productId" />
				</c:if>
				<input type="text" placeholder="${EDITED_PRODUCT eq null?"Set product name" : EDITED_PRODUCT.name}" name="productName" />
				<input type="text" placeholder="${EDITED_PRODUCT eq null?"Set product type" : EDITED_PRODUCT.type}" name="productType" />
				<textarea rows="10" style="width: 100%" placeholder="${EDITED_PRODUCT eq null?"Add product description" : EDITED_PRODUCT.description}" name="productDescription"></textarea>
				<c:if test="${EDITED_PRODUCT ne null}">
					<img src="<%=request.getContextPath()%>${EDITED_PRODUCT.image}" style="height:70px">
				</c:if>
				<input type="file" name="file" />
				<input type="submit" value ="${EDITED_PRODUCT eq null?"Add product" : "Submit changes"}"/>
			</form>
		</div>
	</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>