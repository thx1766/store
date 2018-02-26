<html>
<body>
Supply a valid cartid to view cart items.
<br><a href='/store/listCarts.jsp'>List Carts</a> to view valid carts.
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/listCartItemsRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/listItems"> -->
CartID:<input type="text" name = "cartID" id="cartID">
<br>
<button type="submit" >List Cart Items</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>