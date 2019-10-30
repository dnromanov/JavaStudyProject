
<%@page import="model.User"%>
<%@page import="static util.AppConstants.*"%>
<header>
	<div class="back"></div>
	<%
		User user = (User) session.getAttribute(LOGEDIN);
	%>


		<%
			if (user != null) {
		%>

	<nav>
		<a href="#">About</a>
		<a href="#">Works</a>
		<a href="#">Partners</a>
		<a href="<%=request.getContextPath()%>/products">Products</a>
		<a href="<%=request.getContextPath()%>/welcome">Welcome</a>
		<% if(user.getRole().getId() == 2) { %>
		
		<a href="<%=request.getContextPath()%>/admin">Admin</a>
		
		<% } %>
	</nav>
	<% } %>
</header>