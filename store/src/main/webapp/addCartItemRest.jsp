<%@ page import ="resources.CartsResource" %>
<html>
<head>
</head>
<body>
<%
	CartsResource resource = new CartsResource();
	int cartid = (int) Integer.parseInt(request.getParameter("cartID"));
	int productid = (int) Integer.parseInt(request.getParameter("productID"));
	int quantity = 0;
	boolean invalidquantity=false;
	try{
		quantity = (int) Integer.parseInt(request.getParameter("quantity"));
	}catch(NumberFormatException e){
		invalidquantity=true;
		%>
			You must supply a valid number.
		<%
	}
	if(!invalidquantity){
		if(quantity <= 0){
			%>
			Quantity must be greater than 0<br>
			<%
		}else{
		
			String value = resource.addCartItemNonRest(cartid, productid, quantity);
			%>
			<%=value %>
			<%
		}
	}
%>
<br><form enctype='application/x-www-form-urlencoded' method='POST' action='/store/listCartItemsRest.jsp'><input type='hidden' name = 'cartID' id='cartID' value=<%=cartid %>><button type='submit' >View Cart</button></form></body></html>







</body>
</html>