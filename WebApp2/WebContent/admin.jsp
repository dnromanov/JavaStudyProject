<%@page import="util.CryptoUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="dao.UsersDAOImpl"%>
<%@ page import="model.User"%>
<%@page import="static util.AppConstants.*"%>

<%
	session = request.getSession();
	session.setAttribute(URI_TO_RIDERECT_ROLE, request.getServletPath());
%>
<%
		User admin = (User) session.getAttribute(LOGEDIN);
		if (session.getAttribute(USERS_LIST) != null) {
			List<User> users= (ArrayList) session.getAttribute(USERS_LIST);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome <%=admin.getName()%></title>
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main2.css" />
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin.css" />
<link href="${pageContext.request.contextPath}/css/login-style.css"
	rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
				<%
				if (request.getAttribute(MESSAGE) != null) {
			%>
			<div style="text-align: center; margin-top: 30px"><%=request.getAttribute(MESSAGE)%></div>
			<%
				}
			%>
		<table>
    <thead>
      <tr>
      	<th>Position</th>
        <th>Name</th>
        <th>Email</th>
        <th>Password</th>
        <th>Role</th>
        <th>Edit</th>
        <th>Status</th>
        <th hidden>Change status</th>
        <th>Change status</th>
        <th>Reset password</th>
        <th>Delete</th>
      </tr>
    </thead>
    <tbody>
    	<% int counter = 1;	
    	for (User userItem : users) {


    		
    		%> <tr>
    			<td><%=counter %></td>
				<td><%=userItem.getName() %></td>
				<td><%=userItem.getEmail() %></td>
				<td><%=CryptoUtils.decrypt(userItem.getPassword()) %></td>
				<td><%=userItem.getRole().getName() %></td>
				<td><a href="<%=request.getContextPath()+"/admin?command=edit&id="+userItem.getUid()%>">Edit</a></td>
				<td class="status"><%if(userItem.getStatus().equals("T")) {%>Activated<%} else {%>Deactivated<%} %></td>
				<td hidden><a href="<%=request.getContextPath()+"/admin?command=status&id="+userItem.getUid()%>"><% if(userItem.getStatus().equals("T")) {%>Deactivate<%} else {%>Activate<%}%></a></td>
				<td>
					<select <%if(admin.getUid() == userItem.getUid()) {%> disabled <%} %>onchange="changestatus(this.value, <%=userItem.getUid() %>, <%=counter-1 %>)" name="status">
						<!--  <option disabled selected="selected"><%if(userItem.getStatus().equals("T")) {%>Activated<%} else {%>Deactivated<%} %></option> -->
						<option disabled selected="selected">Choose option</option>
						<option value="d">Deactivate</option>
						<option value="a">Activate</option>
					</select>
				</td>
				<td><a href="<%=request.getContextPath()+"/admin?command=resetPassword&id="+userItem.getUid()%>">Reset Password</a></td>
				<td><a href="<%=request.getContextPath()+"/admin?command=delete&id="+userItem.getUid()%>">Delete</a></td>
			</tr> <%
			counter++;
		} %>
    </tbody>
  </table>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
<%
	} else {
			request.getRequestDispatcher("/admin").forward(request, response);
		}
%>