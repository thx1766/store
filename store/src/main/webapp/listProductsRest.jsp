<%@page import = "resources.ProductsResource" %>
<%@ page import = "java.util.List" %>
<%@ page import = "model.Products" %>
<%@ page import = "java.text.NumberFormat" %>
<html>
<head>
</head>
<body>

<%
	ProductsResource resource = new ProductsResource();
	List<Products> list = resource.listProductsNonRest();
	boolean useridflag=false;
	int userid=0;
	try{
		 userid = (int) Integer.parseInt(request.getParameter("userid"));
	}catch(NumberFormatException e){
		useridflag=true;
		%>
		Number expected for this value.
		<% 
	}
	int cartid = resource.getCartFromUserid(userid);
	if(!useridflag){
		if(cartid==-1){
		%>
			You must create a cart first!<br><a href='/store/addCart.jsp'>Create a cart</a>
		<%	
		}else{
		for(Products table : list){
			%>
			<form method='POST' action='/store/addCartItemRest.jsp'>
			<b><%=table.getProductName() %></b>
			<input type='hidden' name='productID' size='4' value=<%= table.getProduct_id()%>>
			<br>
			<%
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String price = formatter.format(table.getPrice());
			%>
			Price: <%=price %>
			<br>
			Quantity: <input type='text' name='quantity' size='4' value='1'>
			<input type='hidden' name='cartID' size='4' value=<%= cartid%>>
			<br>
			Inventory Quantity: <%=table.getQuantity() %>
			<br>
			<button type='submit' >Add To Cart</button></form>
			<br>
			<%
		}
		}
	}
%>

<br><form enctype='application/x-www-form-urlencoded' method='POST' action='/store/listCartItemsRest.jsp'>
<input type='hidden' name = 'cartID' id='cartID' value=<%= cartid%>>
<button type='submit' >List Cart Items</button>
</form>
<br>
<input type="button" onclick="location.href='/store/customers.jsp';" value="Customer Menu" />
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>
</html>