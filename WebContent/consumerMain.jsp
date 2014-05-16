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
<!-- Main page that is shown in startup -->
	City:<select name="cityChosen">
		<c:forEach items="cities" var="city">
			<option>city name goes here</option>
		</c:forEach>
	</select><br><br>	
	Number of Rooms: <input type="text" name="numrooms"><br><br>
	Check-In: Day<select name="startday">
				<c:forEach var="i" begin="1" end="31">
					<option>${i }</option>
				</c:forEach>
			</select>
		Month<select name="startmonth">
				<c:forEach var="i" begin="00" end="12">
					<option>${i }</option>
				</c:forEach>
			</select>
		Year<select name="startyear">
				<c:forEach var="i" begin="2014" end="2017">
					<option>${i }</option>
				</c:forEach>
			</select><br><br>
	Check-Out: Day<select name="endday">
				<c:forEach var="i" begin="1" end="31">
					<option>${i }</option>
				</c:forEach>
			</select>
		Month<select name="endmonth">
				<c:forEach var="i" begin="00" end="12">
					<option>${i }</option>
				</c:forEach>
			</select>
		Year<select name="endyear">
				<c:forEach var="i" begin="2014" end="2017">
					<option>${i }</option>
				</c:forEach>
			</select><br><br>
	Max Price:<input type="text" name="maxprice">
	<input type="hidden" value="action" name="search">
	<input type="submit" name="Search">
	<input type="hidden" value="action" name="reset">
	<input type="submit" name="Back to Search">
		
</form>
</body>
</html>