<%@ page import ="resources.CartsResource" %>
<html>
<head>
</head>
<body>
<%
	CartsResource resource = new CartsResource();
	int cartid = (int) Integer.parseInt(request.getParameter("cartID"));
	int productid= (int) Integer.parseInt(request.getParameter("productID"));
	int quantity = 0;
	boolean qflag=false;
	try{
		quantity = (int) Integer.parseInt(request.getParameter("quantity"));
	}catch(NumberFormatException e){
		qflag=true;
	%>
		Must enter a valid number.
	<%	
	}
	if (!qflag){
	if (quantity <= 0){
%>
	Quantity must be greater than 0.
	<br>
	<%}else{ 
	String value = resource.updateCartItemNonRest(cartid, productid, quantity);
	%>
	Value: <%=value %>
	<%
	} 
	}
	%>
	<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
<br>
<form enctype='application/x-www-form-urlencoded' method='POST' action='/store/listCartItemsRest.jsp'><input type='hidden' name = 'cartID' id='cartID' value=<%=cartid %>><button type='submit' >View Cart</button></form>
</body>
</html>