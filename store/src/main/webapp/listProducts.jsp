<html>
<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/listProductsRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/products/list"> -->

List Products
<input type="hidden" name="userid" value=<%= session.getAttribute("userid")%>>
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>