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

<!-- print out the cusomterbooking details -->
	<c:forEach items="${details}" var="list">
			<tr>
				<td>${list.hotelname}</td>
				<td>${list.roomtype}</td>
				<td>${list.startdate}</td>
				<td>${list.enddate}</td>
				<td>$list.firstname}</td>
				<td>$list.lastname}</td>
			</tr>
	</c:forEach>

</body>
</html>