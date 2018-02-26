<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/deleteCartItemRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/deleteCartItem"> -->
Cart ID: <input type="text" name = "cartID"/>
Product ID: <input type="text" name = "productID"/>
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>