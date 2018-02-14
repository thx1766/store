<html>

<%
int userid =(int) session.getAttribute("userid");
%>

<body>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/add" >
<!-- CustomerID:  --><input type="hidden" name = "customerID" id="customerID" value="<%=userid%>"/>

<button type="submit" >Create Cart</button>
</form>


<br>
<form action="/store/administrators.jsp">
    <input type="submit" value="Admin Menu" />
</form>
</body>

</html>