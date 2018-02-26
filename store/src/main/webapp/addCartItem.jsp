<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/addCartItemRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/addCartItem"> -->
CartID: <input type="text" name = "cartID"/>
ProductID: <input type="text" name = "productID"/>
Quantity: <input type="text" name = "quantity"/>
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>