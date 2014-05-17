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
<form action="ConsumerServlet" method="POST">
<!-- search results shown here -->
	<table>
		<tr>
			<th>City</th>
			<th>RoomType</th>
			<th>Num Available</th>
			<th>Cost</th>
			<th>Select</th>
		</tr>
		<tr>
		<c:forEach items="${resultlist }" var="list">
			<td>${list.city}</td>
			<td>${list.roomtype}</td>
			<td>${list.count}</td>
			<td>${list.price}</td>
			<td><input type="checkbox" name="selectedrooms[]" value="${list.roomtype}">
			</td>
		</c:forEach>
		</tr>
	</table>
	<input type="hidden" name="action" value="confirm">
	<input type="submit" value="Confirm">
	<input type="hidden" name="city" value="${city}" >
	<input type="hidden" name=checkin value= "${checkin}">
	<input type="hidden" name=checkout value= "${checkout}">
	<input type="hidden" name="action" value="reset">
	<input type="submit" value="Back to Search">
</form>
</body>
</html>