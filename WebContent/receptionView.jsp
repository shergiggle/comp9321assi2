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
		<input type="hidden" name="action" value="logout">
		<input type="submit" value="Logout">
	</form><br><br>
	${message}<br>
<table>
	<tr>
	<td>
	Rooms Available
		<table>
			<tr>
				<th>Room Number</th>
				<th>Room Type</th>
			</tr>
			<c:forEach items="${roomsOccupied}" var="room">
			<tr>
				<td>${room.roomNumber}</td>
				<td>${room.type}</td>
			</tr>
			</c:forEach>
		</table>
	</td>
	<td>
	Bookings to Check-In
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Room Type</th>
				<th>Select</th>
			</tr>
			<c:forEach items="${bookingsStillProcess}" var="booking">
			<tr>
				<td>${booking.firstname}</td>
				<td>${booking.lastname}</td>
				<td>${booking.roomtype}</td>
				<td><input type="radio" value="${booking.id}"></td>
			</tr>
			</c:forEach>
		</table>
	</td>
	</tr>
</table>
</body>
</html>