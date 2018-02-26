<%@ page import ="resources.CustomersResource" %>
<html>
<head>
</head>
<body>

<%
CustomersResource resource = new CustomersResource();
String fname = request.getParameter("firstName");
String lname = request.getParameter("lastName");
String email = request.getParameter("email");
String gender = request.getParameter("gender");
String username = request.getParameter("username");
String value = resource.addCustomerNonRest(fname, lname, email, gender, username);
%>
User Added:
<br>
<%=value %>
<br>
<a href="/store/testlogin1.jsp">Login</a>
</body>
</html>