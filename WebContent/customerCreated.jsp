<%@page language="java"%>
<%@page import="model.DataManager" %>
		
<%
	String userName = request.getParameter("userName");
	String password = request.getParameter("password");
	DataManager dataManager = new DataManager("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/webshop", "root", "q");
	dataManager.createCustomer(userName, password);
%>
Account created. </br>
Username: <%=userName%> </br>
Password: <%=password%>
<form action="index.jsp" method="post">
<input type="submit" value="Back to log in page"/>
</form>
