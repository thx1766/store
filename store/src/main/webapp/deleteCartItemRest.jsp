<%@ page import = "resources.CartsResource" %>

<html>
<head>
</head>
<body>
<%
	CartsResource resource = new CartsResource();
	int cartid = (int) Integer.parseInt(request.getParameter("cartID"));
	int productid = (int) Integer.parseInt(request.getParameter("productID"));
	String value = resource.deleteCartItemNonRest(cartid, productid);
%>
Product deleted:
<br>
<%=value %>
<br>
<form enctype='application/x-www-form-urlencoded' method='POST' action='/store/listCartItemsRest.jsp'><input type='hidden' name = 'cartID' id='cartID' value=<%=cartid %>>
<button type='submit' >View Cart</button>
</form>

</body>
</html>