<html>
<body>
<h1>Become a new user!</h1>
<form enctype="application/x-www-form-urlencoded" method="POST" action="/store/addCustomerRest.jsp">
<!-- <form enctype="application/x-www-form-urlencoded" method="POST" action="/store/webapi/customer/add"> -->
First Name:<input type="text" name="firstName" />
<br>Last Name:<input type="text" name="lastName" />
<br>Email Address:<input type="text" name="email">
 <table><tr><td>Gender:</td></tr>
 <tr><td><input type="radio" name="gender" value="male" > Male</td></tr>
  <tr><td><input type="radio" name="gender" value="female"> Female</td></tr>
  <tr><td><input type="radio" name="gender" value="other"> Other</td></tr>
  </table> 
  <br>Username:<input type="text" name="username">
<button type="submit" >submit</button>
</form>
<br>
<input type="button" onclick="location.href='/store/testlogin1.jsp';" value="Login" />
<br>
<input type="button" onclick="location.href='/store/administrators.jsp';" value="Admin Menu" />
</body>

</html>