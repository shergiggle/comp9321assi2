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
<form action="consumer" method="POST">
<!-- Main page that is shown in startup -->
	City:<select name="cityChosen">
		<option value="Sydney">Sydney</option>
		<option value="Melbourne">Melbourne</option>
		<option value="Brisbane">Brisbane</option>
		<option value="Adelaide">Adelaide</option>
		<option value="Hobart">Hobart</option>
	</select><br><br>	
	Number of Rooms: <input type="text" name="numrooms"><br><br>
	Check-In: Day<select name="startday">
				<c:forEach var="i" begin="1" end="31">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
		Month<select name="startmonth">
				<c:forEach var="i" begin="00" end="12">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
		Year<select name="startyear">
				<c:forEach var="i" begin="2014" end="2017">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select><br><br>
	Check-Out: Day<select name="endday">
				<c:forEach var="i" begin="1" end="31">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
		Month<select name="endmonth">
				<c:forEach var="i" begin="00" end="12">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
		Year<select name="endyear">
				<c:forEach var="i" begin="2014" end="2017">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select><br><br>
	Max Price:<input type="text" name="maxprice"><br><br>
	<input type="hidden" name="action" value="search">
	<input type="submit" value="Search">
	<input type="hidden" name="action" value="reset">
	<input type="submit" value="Back to Search">
		
</form>
</body>
</html>