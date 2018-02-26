<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/updateCartRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/update"> -->
CartID: <input type="text" name = "cartID"/>
CustomerID: <input type="text" name = "customerID"/>
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>