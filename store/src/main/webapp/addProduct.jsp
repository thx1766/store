<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/addProductRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/products/add"> -->
Product Name: <input type="text" name = "productName"/>
Price: <input type="text" name = "price"/>
Quantity: <input type="text" name = "quantity"/>
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>