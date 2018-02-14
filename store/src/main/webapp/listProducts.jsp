<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/products/list">

List Products
<input type="hidden" name="userid" value=<%= session.getAttribute("userid")%>>
<button type="submit" >submit</button>
</form>
<br>
<form action="/store/customers.jsp">
    <input type="submit" value="Customer Menu" />
</form>
</body>

</html>