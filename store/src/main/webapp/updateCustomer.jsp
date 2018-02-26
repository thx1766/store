<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/updateCustomerRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/customer/update"> -->
CustomerID: <input type="text" name = "customerID"/>
FirstName:<input type="text" name="firstName" />
LastName:<input type="text" name="lastName" />
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>