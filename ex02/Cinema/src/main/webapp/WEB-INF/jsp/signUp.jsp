
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">

</head>
<body>

  <h2> Registration </h2>
  </div>
<form action="<%= request.getContextPath() %>/signUp" method="post" >

  First Name: <input type="text" name="firstName">
  <br> <br>

  Last Name: <input type="text" name="lastName">
  <br> <br>

  Email: <input type="email" name="email">
  <br> <br>

  Password: <input type="password" name="password">
  <br> <br>

  Phone number: <input type="text" name="phoneNumber"><br>

  <br>
  <input type="submit" value="Register" >

</form>

</body>
</html>