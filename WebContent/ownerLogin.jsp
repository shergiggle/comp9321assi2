<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Management</title>
</head>
<body>
	<form action="owner" method="POST">
		<br><br>${message }<br>
		User ID:<input type="text" name="userid"><br>
		Password:<input type="text" name="password"><br>
		<input type="hidden" name="action" value="login">
		<input type="submit" value="Login">
	</form>
</body>
</html>