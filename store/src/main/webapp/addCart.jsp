<html>

<%
int userid =(int) session.getAttribute("userid");
%>

<body>
<!--<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/add" > -->
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/addCartRest.jsp" >
<!-- CustomerID:  --><input type="hidden" name = "customerID" id="customerID" value="<%=userid%>"/>

<button type="submit" >Create Cart</button>
</form>


<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>