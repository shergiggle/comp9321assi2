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
		<input type="hidden" name="action" value="discount">
		<input type="submit" value="New Discounts">
	</form>
	${message }
<table>
	<tr>
		<th>Hotel Name</th>
		<th>Total Rooms Occupied</th>
		<th>Total Rooms Available</th>
	</tr>
	<tr>
		<c:forEach items = "availabilityList" var="list">
		<td>${list.name }</td>
		<td>${list. }</td>
		<td>${list. }</td>
		</c:forEach>
	</tr>
</table>
</body>
</html>