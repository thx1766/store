<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/listProductsRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/products/list"> -->
List Products
<br><br>
Provide a valid userid number to associate cart actions.
<br><a href='/store/listCarts.jsp'>List Carts</a> to view userids with valid carts.
<br><br>
Userid:<input type="text" name="userid" >
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>