<html>
<body>
<%@ page import = "javax.persistence.EntityManagerFactory" %>
<%@ page import ="javax.persistence.Persistence"  %>
<%@ page import ="javax.persistence.EntityManager"  %>
<%@ page import ="javax.persistence.Query"  %>
<%@ page import = "java.util.List" %>
<%@ page import =  "model.Customers" %>
<%@ page import ="model.Carts"  %>
<%@ page import = "resources.CartsResource" %>


<%! @SuppressWarnings("unchecked") %>
<%
	int userid =(int) session.getAttribute("userid");
	CartsResource cr = new CartsResource();
	int mycartid = cr.getCartFromUserid(userid);
//begin removal

//end removal
%>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/listCartItemsRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/listItems"> -->
<!-- CartID: --> <input type="hidden" name = "cartID" id="cartID" value="<%=mycartid %>"/>
<button type="submit" >List Cart Items</button>
</form>
<br>
<input type="button" onclick="location.href='/store/customers.jsp';" value="Customer Menu" />
</body>

</html>