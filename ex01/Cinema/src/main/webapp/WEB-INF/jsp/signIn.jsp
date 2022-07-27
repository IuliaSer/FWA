<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
</head>
<body>

<div id="action_form">
    <h1 > Log in</h1>
    <form action="<%= request.getContextPath() %>/signIn" method="post">

    Email: <input type="email" name="email">
    <br> <br>

    Password: <input type="password" name="password"><br>

    <br>
    <input type="submit" value="log in">

</form>
</div>
</body>
</html>
