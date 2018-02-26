<%@ page import = "resources.CartsResource" %>

<html>
<head>
</head>
<body>
<% 
CartsResource resource = new CartsResource();
int id =(int)  Integer.parseInt(request.getParameter("customerID")) ;
int cartid = resource.addCartNonRest(id);
%>
Cart added:<%=cartid%><br><a href='/store/listProducts.jsp'>List Products</a>
</body>
</html>