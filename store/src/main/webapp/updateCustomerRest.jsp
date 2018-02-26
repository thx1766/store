<%@ page import ="resources.CustomersResource" %>
<html>
<head>
</head>
<body>
<%
	CustomersResource resource = new CustomersResource();
	int customerid=0;
	boolean cidflag=false;
	try{
		customerid = (int) Integer.parseInt(request.getParameter("customerID"));
	}catch(NumberFormatException e){
		cidflag=true;
		%>
		Customer id must be a number.
		<%
	}
	if(!cidflag){
		String firstname = request.getParameter("firstName");
		String lastname = request.getParameter("lastName");
		String value = resource.updateCustomerNonRest(customerid, firstname, lastname);
		%>
		Customer Updated:
		<br>
		<%=value %>
<%} %>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>
</html>