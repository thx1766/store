<%@ page import="resources.ProductsResource" %>
<html>
<head>
</head>
<body>
<%
	ProductsResource resource = new ProductsResource();
	String productname = request.getParameter("productName");
	double price = 0;
	boolean priceinvalid=false;
	try{
		 price = (double) Double.parseDouble(request.getParameter("price"));
	}catch(NumberFormatException e){
		priceinvalid=true;
		%>
		You must supply a number for price.
		<br>
		<input type="button" onclick="location.href='/store/customers.jsp';" value="Customer Menu" />
		<br>
		<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
		<%
	}
	if(!priceinvalid){
		int quantity=0;
		boolean quantitycheck=false;
		try{
			quantity = (int) Integer.parseInt(request.getParameter("quantity"));
		}catch(NumberFormatException e){
			quantitycheck=true;
			%>
			You must supply a number for price.
			<br>
			<input type="button" onclick="location.href='/store/customers.jsp';" value="Customer Menu" />
			<br>
			<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
			<%
		}
		if(!quantitycheck){
			if(price<=0) {
				%>
				Price must be a positive value<br><a href='/store/addProduct.jsp'>Return</a>
				<%
			}
			
			if(quantity<=0) {
				%>
				 Quantity must be a positive value<br><a href='/store/addProduct.jsp'>Return</a>
				<%
			}
			
			String value = resource.addProductNonRest(productname, price, quantity);
			
	
	%>
	
	Product added:
	<br>
	<%=value %>
	<br>
	<a href="/store/addProduct.jsp">Add Another Product</a>
	<br>
	<a href="/store/administrators.jsp">Admin Menu</a>
<%} }%>

</body>
</html>