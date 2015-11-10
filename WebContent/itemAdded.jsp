<%@page language="java"%>
<%@page import="model.DataManager" %>
		This item has been added: 
		
<%
	String customerName = request.getParameter("customerName");
	String itemName = request.getParameter("itemName");
	DataManager dataManager = new DataManager("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/webshop", "root", "q");
	dataManager.addItemToShoppingCart(customerName, itemName);
%>
<%=itemName%> added to user <%=customerName%>.
<form action="ShoppingCart.jsp" method="post">
	<input type="hidden" name="customerName" value="<%=customerName%>"/>
	<input type="submit" value="View my shopping cart"/>
</form>
