<%@page import = "resources.ProductsResource" %>
<html>
<head>
</head>
<body>

<% 
	ProductsResource resource = new ProductsResource();
	int productid=0;
	boolean productidcheck=false;
	try{
	productid = (int) Integer.parseInt(request.getParameter("productID"));
	}catch(NumberFormatException e){
		productidcheck=true;
		%>
			You must supply a number for this field.
		<%
	}
	if(!productidcheck){
		try{
			String value = resource.deleteProductNonRest(productid);
			%>
			Product deleted:
			<br>
			<%=value %>
			<br>
	<% 
		}catch(Exception e){
		
			%>
			Error processing that product id. It may not exist.
			<%
		}
	}
%>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>
</html>