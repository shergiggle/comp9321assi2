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
	Booking ID: ${bookingid }
	Room To Check-In:<input type="text" name="checkinroom">
	<input type="hidden" name="action" value="checkinroom">
	<input type="submit" value="Check-In"><br>
	<input type="hidden" name="action=" value="cancel">
	<input type="submit" value="Cancel"><br><br>
	</form>
	Rooms Available for Checkin<br>
	<table>
		<tr>
		 <th>Room Number</th>
		</tr>
		<c:forEach items="${availableRooms }" var="ar">
		<tr>
			<td>${ar.roomnumber }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>