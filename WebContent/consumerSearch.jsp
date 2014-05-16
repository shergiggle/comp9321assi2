<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Management</title>
</head>
<body>
<form action="" method="POST">
<!-- search results shown here -->
	<table>
		<tr>
			<th>City<th>
			<th>Name<th>
			<th>Num Available<th>
			<th>Cost<th>
			<th>Number of Rooms<th>
		</tr>
		<tr>
		<c:forEach items="${resultlist }" var="list">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><input type="text" name="numrooms"></td>
		</c:forEach>
		</tr>
	</table>
	<input type="hidden" name="action" value="confirm">
	<input type="submit" value="Confirm">
	<input type="hidden" name="action" value="reset">
	<input type="submit" value="Back to Search">
</form>
</body>
</html>