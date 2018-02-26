<%@ page import = "resources.CartsResource" %>
<%@ page import = "java.util.List" %>
<%@ page import = "model.Carts" %>
<html>
<head>
</head>
<body>
<%
CartsResource resource = new CartsResource();
List<Carts> mylist= resource.listCartsNonRest();
for(Carts table :mylist){
%>
	CartID: <%= table.getCart_id() %>
	CustomerID: <%= table.getCustomer().getCustomer_id() %>
	<br>
	
<%
}
%>
<BR><a href='/store/deleteCart.jsp'>Delete carts</a><a></a>
<BR><a href='/store/listProductsAdmin.jsp'>Admin List Products</a>
<BR><a href='/store/listCartItemsAdmin.jsp'>Admin List Cart Items</a>
</body>
</html>