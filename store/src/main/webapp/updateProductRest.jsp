<%@ page import = "resources.ProductsResource" %>
<html>
<head>
</head>
<body>
<%
	ProductsResource resource = new ProductsResource();
	int productid=0;
	boolean productidflag=false;
	try{
		productid = (int) Integer.parseInt(request.getParameter("productID"));
	}catch(NumberFormatException e){
		productidflag=true;
		%>
		You must supply a number for product id.
		<br>
		<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
		<%
	}
	if(!productidflag){
		String productname = request.getParameter("productName");
		double price =0;
		boolean priceflag=false;
		try{
			price = (double) Double.parseDouble(request.getParameter("price"));
		}catch(NumberFormatException e){
			priceflag=true;
			%>
			Price must be a number.
			<br>
			<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
			<%
		}
		if(!priceflag){
			int quantity=0;
			boolean quantityflag=false;
			try{
				quantity = (int) Integer.parseInt(request.getParameter("quantity"));
			}
			catch(NumberFormatException e){
				quantityflag=true;
				%>
				Quantity must be a number.
				<br>
				<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
				<%
			}
			if(!quantityflag){
			String value = resource.updateProductNonRest(productid, productname, price, quantity);
			%>
			Value: <%=value %>

<% } } }%>
</body>
</html>