<%@ page import = "resources.CartsResource" %>
<html>
<head>
</head>
<body>
<%
	CartsResource resource = new CartsResource();
	int cartid = (int) Integer.parseInt(request.getParameter("cartID"));
	int customerid = (int) Integer.parseInt(request.getParameter("customerID"));
	String result="";
	try{
		result += resource.updateCartNonRest(cartid, customerid);
	}catch(Exception e){
		result="database failure<BR>Error message:<br>"+e.getMessage();
	}
%>
result:<%=result %>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>
</html>