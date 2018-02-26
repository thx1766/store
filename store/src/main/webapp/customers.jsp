<html>
<head>
<style>


td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}


</style>
</head>


<%@ page import = "resources.ProductsResource" %>
<body>
<h2>Welcome <%= session.getAttribute("username") %>!</h2>
    <table>
    	<tr><td>Products</td></tr>
    	<tr><td><form enctype="application/x-www-form-urlencoded" method="POST" action="/store/listProductsRest.jsp"><input type="hidden" name="userid" value=<%= session.getAttribute("userid")%>><button type="submit" >List Products</button></form></td></tr>
    	<tr><td>Cart Items</td></tr>
    	<tr><td>
    	<%
			int userid =(int) session.getAttribute("userid");
			ProductsResource pr = new ProductsResource();
			int mycartid=pr.getCartFromUserid(userid);
		%>
		<% if(mycartid==-1) {%>
			No cart created yet.
		<%}else{ %>
			<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/listCartItemsRest.jsp"><input type="hidden" name = "cartID" id="cartID" value="<%=mycartid %>"/><button type="submit" >List Cart Items</button></form></td></tr>
		<%} %>
    </table>
</body></html>