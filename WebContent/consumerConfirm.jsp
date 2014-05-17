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
	<form action="" method="POST">
		<!-- search results shown here without number of rooms selection -->
		<table>
			<tr>
				<th>something
				<th>
			</tr>
		</table><br><br>
		<input type="hidden" name="action" value="checkout"> 
		<input type="submit" value="Checkout"><br> 
		<input type="hidden" name="action" value="reset"> 
		<input type="submit" value="Back to Search">
	</form>
</body>
</html>