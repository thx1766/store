<%@ page import="resources.LoginResource" %>
<%@ page import="transferObjects.loginTransferObject" %>

<%
	String name = request.getParameter( "username" );
	session.setAttribute( "username", name);
	LoginResource resource=new LoginResource();
	loginTransferObject result = resource.loginmethod(name);
	int userid = result.getUserid();
	session.setAttribute( "userid", userid );

%>
<HTML>
<% if(userid!=-1){
%>
<head>
<meta http-equiv="refresh" content="1; URL=http://localhost:3080/store/customers.jsp">
</head>
<%} %>
<BODY>
<% if(userid!=-1){%>
<h2>Login succeeded!<br></h2>Automatically redirecting in 1 second:<br>
<A HREF="customers.jsp">Continue</A>
<%}else{ %>
<h2>Login failed for username: <%=request.getParameter( "username" ) %></h2>
<A href="testlogin1.jsp">Return</A>
<%} %>
</BODY>
</HTML>