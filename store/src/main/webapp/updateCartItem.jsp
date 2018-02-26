<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/updateCartItemRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/updateCartItem"> -->
CartID: <input type="text" name = "cartID"/>
Product ID: <input type="text" name = "productID"/>
Quantity: <input type="text" name = "quantity"/>
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>