<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.DataManager" %>
<%@page import="beans.ItemBean" %>
<%@page import="java.util.ArrayList" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create account</title>
	</head>
	


	<body>
		<form action="customerCreated.jsp" method="post">
		
			Username: <input type="text" id="userName" name="userName" /> </br>
			Password: <input type="password" id="password" name="password"/> </br>
		
			<input type="submit" value="Create my account" />
		</form>
	</body>
	
</html>