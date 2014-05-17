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
<!-- returns the confirmation of the discount -->
	City: ${city }<br>
	Hotel: ${hotelname }<br>
	Room Type: ${type }<br>
	Start Date: ${startdate }<br>
	End Date: ${enddate }<br>
	Amount: ${amount }%<br>
	<input type="hidden" name="action" value="confirm">
	<input type="submit" value="Confirm">
	<input type="hidden" name="action" value="reset">
	<input type="submit" value="Cancel">
	</form>
</body>
</html>