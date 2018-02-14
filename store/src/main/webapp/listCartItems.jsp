<html>
<body>
<%@ page import = "javax.persistence.EntityManagerFactory" %>
<%@ page import ="javax.persistence.Persistence"  %>
<%@ page import ="javax.persistence.EntityManager"  %>
<%@ page import ="javax.persistence.Query"  %>
<%@ page import = "java.util.List" %>
<%@ page import =  "model.Customers" %>
<%@ page import ="model.Carts"  %>


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
	Carts mycart = cartlist.get(0);
	int mycartid = mycart.getCart_id();
	System.out.println("cartid:"+mycartid);
%>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/carts/listItems">
<!-- CartID: --> <input type="hidden" name = "cartID" id="cartID" value="<%=mycartid %>"/>
<button type="submit" >List Cart Items</button>
</form>
<br>
<form action="/store/">
    <input type="submit" value="Main Menu" />
</form>
</body>

</html>