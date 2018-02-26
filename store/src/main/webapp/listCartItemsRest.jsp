<%@ page import="resources.CartsResource" %>
<%@ page import="model.CustomerProducts" %>
<%@ page import ="java.util.List" %>
<%@ page import ="java.text.NumberFormat" %>
<html>
<head>
</head>
<body>
<%
int cartid = (int) Integer.parseInt(request.getParameter("cartID"));
CartsResource resource = new CartsResource();
int userid = resource.getUseridFromCartid(cartid);
List<CustomerProducts> mylist = resource.listCartContents(cartid);
if(mylist==null){
%>
<h1>Empty List</h1><br>
<%
}else{
	%>
	<table><tr>
	<%
	for(CustomerProducts table: mylist){
		%>
		<td>
		<%=table.getProductName() %>
		<% NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String price =formatter.format(table.getPrice()); 
			String subtotal = formatter.format(table.getPrice()*table.getQuantity());
		%>
		<BR>price: <%=price %>
		<BR>item subtotal: <%=subtotal %>
		<br>
		<form enctype='application/x-www-form-urlencoded' method='POST' action='/store/deleteCartItemRest.jsp'>
		<input type='hidden' name = 'cartID' value=<%=cartid %>>
		<input type='hidden' name = 'productID' value=<%=table.getProduct_id() %>>
		<button type='submit' >Delete From Cart</button>
		</form>
		<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/updateCartItemRest.jsp">
		<input type='hidden' name = 'cartID' value=<%=cartid %>>
		<input type='hidden' name = 'productID' value=<%=table.getProduct_id()%>>
		Quantity:<input type='text' size=4 name = 'quantity' value=<%= table.getQuantity()%>>
		<button type='submit' >Update Quantity</button>	
		</form></td>
		<%
	}
	%>
	</tr></table><br>
	<form enctype="application/x-www-form-urlencoded" method="POST" action='/store/listProductsRest.jsp'>
	<input type='hidden' name='userid' value=<%=userid %>>
	<button type='submit' >List Products</button>
	</form>
	<form action='/store/customers.jsp'><input type='submit' value='Customer Menu' /></form>
	<%
	
}
%>
</body>
</html>