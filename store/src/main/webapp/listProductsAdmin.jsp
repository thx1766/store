<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/products/list">
List Products
<br><br>
Provide a valid userid number to associate cart actions.
<br><a href='/store/listCarts.jsp'>List Carts</a> to view userids with valid carts.
<br><br>
Userid:<input type="text" name="userid" >
<button type="submit" >submit</button>
</form>
<br>
<form action="/store/administrators.jsp">
    <input type="submit" value="Admin Menu" />
</form>
</body>

</html>