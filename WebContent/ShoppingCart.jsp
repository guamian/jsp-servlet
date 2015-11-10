<%@page import="model.DataManager" %>
<%@page import="beans.ItemBean" %>
<%@page import="java.util.ArrayList" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>My Shopping Cart</title>
	</head>
	
	<%
		DataManager dataManager = new DataManager("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/webshop", "root", "q");
		String customerName = request.getParameter("customerName");
		ArrayList<ItemBean> shoppingCartList = dataManager.showShoppingCart(customerName);
		if(shoppingCartList.size() == 0) {
	%>

	<h2>Your shopping cart is empty. Buy something!</h2>

	<%
		} else {
	%>

	<h2>
		You have <%=shoppingCartList.size()%> items in your shopping cart.
	</h2>

	<%
		    for(ItemBean i: shoppingCartList) {
			    out.println(i.getName());
			    out.println("</br>");

			    out.println((int)i.getPrice() + " SEK");
			    out.println("</br></br>");
			}
			
		}
	%>
	
	
</html>