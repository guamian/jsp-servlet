<%@ page contentType="text/html" pageEncoding="UTF-8"%>
    

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>The web shop log in</title>
	</head>

	<body>
		<form action="LoginServlet" method="get">
		
			Username: <input type="text" id="userName" name="userName" />
			Password: <input type="password" id="password" name="password"/>
		
			<input type="submit" value="Login" />
		</form>
		
		<form action="createCustomer.jsp" method="get">
			<input type="submit" value="Create new user" />
		</form>
	</body>
</html>