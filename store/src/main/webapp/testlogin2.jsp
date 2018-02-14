<%@ page import = "javax.persistence.EntityManagerFactory" %>
<%@ page import= "javax.persistence.Persistence" %>
<%@ page import = "javax.persistence.EntityManager" %>
<%@ page import="javax.persistence.TypedQuery" %>
<%@ page import= "java.util.List" %>
<%@ page import= "model.Customers" %>


<%
	String name = request.getParameter( "username" );
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	EntityManagerFactory factory;
	factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	EntityManager em = factory.createEntityManager();
	 em.getTransaction().begin();
	  TypedQuery<Customers> q = em.createQuery("select t from Customers t where t.username=?1",Customers.class).setParameter(1, name).setMaxResults(1);
	 List<Customers> myCustomers = q.getResultList();
	 int userpersistedid=-1;
	 if(myCustomers.size()==0){
		 userpersistedid =  -1;
	 }
	 try{
	 Customers myCustomer = myCustomers.get(0);
	 String username = myCustomer.getUsername();
	 userpersistedid = myCustomer.getCustomer_id();
	 }catch(Exception e){
		 session.setAttribute( "userid", -1 );
	 }
	 
	 
	session.setAttribute( "userid", userpersistedid );
	session.setAttribute( "username", name);
%>
<HTML>
<% if(userpersistedid!=-1){
%>
<head>
<meta http-equiv="refresh" content="1; URL=http://localhost:3080/store/customers.jsp">
</head>
<%} %>
<BODY>
<% if(userpersistedid!=-1){%>
<h2>Login succeeded!<br></h2>Automatically redirecting in 1 second:<br>
<A HREF="customers.jsp">Continue</A>
<%}else{ %>
<h2>Login failed for username: <%=request.getParameter( "username" ) %></h2>
<A href="testlogin1.jsp">Return</A>
<%} %>
</BODY>
</HTML>