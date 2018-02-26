<%@ page import = "resources.CustomersResource" %>
<%@ page import ="java.util.List" %>
<%@ page import ="model.Customers" %>
<html>
<head>
</head>
<body>

<%
	CustomersResource resource = new CustomersResource();
	List<Customers> list = resource.listCustomersNonRest();
	for(Customers table: list){
	%>
		Customerid: <%=table.getCustomer_id() %><br>
		First: <%=table.getFirstname() %>
		Last: <%= table.getLastname() %>
		<br><br> 
	<%		
	}
%>
<BR><a href='/store/deleteCustomer.jsp'>Delete Customers</a>
</body>
</html>