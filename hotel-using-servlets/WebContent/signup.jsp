<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up</title>
</head>
<body>
	<form action="signup" method="post">
		<h3>First name: <input type="text" name="first_name"/></h3>
		<h3>Last name: <input type="text" name="last_name"/></h3>
		<h3>Phone number: <input type="text" name="phone"/></h3>
		<h3>Email: <input type="text" name="email"/></h3>
		<h3>Password: <input type="password" name="password"/></h3>
		<input type="submit" value="Sign up"/>
	</form>
</body>
</html>