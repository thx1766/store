<%@ page import = "resources.CartsResource" %>
<html>
<head>
</head>
<body>
<%
String value="";
try{
	int cartid = (int) Integer.parseInt(request.getParameter("cartID"));
	CartsResource resource = new CartsResource();
	value = resource.deleteCartRest(cartid);
}catch(Exception e){
%>
Error: must supply a user id
<%	
}

%>
<%=value %>
<BR><a href='/store/listCarts.jsp'>List Carts</a>
<br><a href='/store/deleteCustomer.jsp'>Delete Customer</a>
</body>
</html>