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
	<form action="reception" method="POST">
		<input type="hidden" name="action" value="checkin">
		<input type="submit" value="Room Check-In">
		<input type="hidden" name="action" value="checkout">
		<input type="submit" value="Room Check-Out">
	</form>
	<table>
	<tr>
	<td>
		<table>
			<tr>
				<th>Rooms Occupied</th>
			</tr>
			<tr>
				<c:forEach items="${roomsOccupied }" var="room">
					<td>room number</td>
				</c:forEach>
			</tr>
		</table>
	</td>
	<td>
		<table>
			<tr>
				<th>Bookings to Check-In</th>
				<th>Select</th>
			</tr>
			<tr>
				<c:forEach items="${bookingsStillProcess }" var="booking">
					<td>booking name</td>
					<td><input type="radio" value="${booking.id }"></td>
				</c:forEach>
			</tr>
		</table>
	</td>
	</tr>
	</table>
</body>
</html>