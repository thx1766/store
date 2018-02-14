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
<%@ page import = "javax.persistence.EntityManagerFactory" %>
<%@ page import ="javax.persistence.Persistence"  %>
<%@ page import ="javax.persistence.EntityManager"  %>
<%@ page import ="javax.persistence.Query"  %>
<%@ page import = "java.util.List" %>
<%@ page import =  "model.Customers" %>
<%@ page import ="model.Carts"  %>
<body>
<h2>Welcome <%= session.getAttribute("username") %>!</h2>
    <table>
    	<tr><td>Products</td></tr>
    	<tr><td><form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/products/list"><input type="hidden" name="userid" value=<%= session.getAttribute("userid")%>><button type="submit" >List Products</button></form></td></tr>
    	<tr><td>Cart Items</td></tr>
    	<tr><td>
    	<%! @SuppressWarnings("unchecked") %>
		<%
			int userid =(int) session.getAttribute("userid");
			String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
			EntityManagerFactory factory;
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			Query q = em.createQuery("select t from Customers t where t.customer_id = ?1",Customers.class).setParameter(1, userid);
			List<Customers> customerlist =  q.getResultList();
			Customers mycustomer = customerlist.get(0);
			Query q2 = em.createQuery("select t from Carts t where t.customer=?1",Carts.class).setParameter(1, mycustomer);
			List<Carts> cartlist = q2.getResultList();
			int mycartid =0;
			try{
				Carts mycart = cartlist.get(0);
				mycartid = mycart.getCart_id();
			}catch(Exception e){
				mycartid=-1;
			}
		%>
		<% if(mycartid==-1) {%>
			No cart created yet.
		<%}else{ %>
			<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/listItems"><input type="hidden" name = "cartID" id="cartID" value="<%=mycartid %>"/><button type="submit" >List Cart Items</button></form></td></tr>
		<%} %>
    </table>
</body></html>