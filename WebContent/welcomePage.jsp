<%@page import="model.DataManager" %>
<%@page import="beans.ItemBean" %>
<%@page import="java.util.ArrayList" %>


<h1>Welcome to the web shop,
	<%String loggedInAs = request.getParameter("userName");%>
	<%=loggedInAs%>!
</h1>

<% //show full item list
	DataManager dataManager = new DataManager("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/webshop", "root", "q");
	ArrayList<ItemBean> listOfItems = dataManager.getFullItemList();
	if(listOfItems.size() == 0) {
		out.println("<h2>No item available.</br></h2>");
	}
	for(ItemBean i: listOfItems) {
		out.println(i.getName());
		out.println("</br>");
		
		out.println((int)i.getPrice() + " SEK");
		
		// Display "add item to shopping cart" link
		
		/* Step by step, after link clicked...:
		    1. Add the item to database.
			2. Forward to itemAdded.jsp. in itemAdded.jsp, show link to "My Shopping Cart" and back to "main page"
		 */

%>
		<form action="itemAdded.jsp" method="post">
		<input type="hidden" name="customerName" value="<%=loggedInAs%>"/>
		<input type="hidden" name="itemName" value="<%=i.getName()%>"/>
		<input type="submit" value="Add to Cart"/>
		</form>
<%
	}
%>		<form action="ShoppingCart.jsp" method="post">
		<input type="hidden" name="customerName" value="<%=loggedInAs%>"/>
		<input type="submit" value="View my shopping cart"/>
		</form>