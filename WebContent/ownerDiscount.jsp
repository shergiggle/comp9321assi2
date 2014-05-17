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
City: <input type="text" name="city"><br>
Hotel:<input type="text" name="hotel"><br>
Room Type:<input type="text" name="type"><br>
Start: Day<select name="startday">
				<c:forEach var="i" begin="1" end="31">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
		Month<select name="startmonth">
				<c:forEach var="i" begin="01" end="12">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select>
		Year<select name="startyear">
				<c:forEach var="i" begin="2014" end="2017">
					<option value="${i }">${i }</option>
				</c:forEach>
			</select><br>
End: Day<select name="endday">
			<c:forEach var="i" begin="1" end="31">
				<option value="${i }">${i }</option>
			</c:forEach>
		</select>
			
	Month<select name="endmonth">
		<c:forEach var="i" begin="01" end="12">
			<option value="${i }">${i }</option>
		</c:forEach>
		</select>
	Year<select name="endyear">
		<c:forEach var="i" begin="2014" end="2017">
			<option value="${i }">${i }</option>
		</c:forEach>
		</select><br>
		
Amount: <select name="amount">
		<c:forEach var="i" begin="00" end="55" step="5">
			<option value="${i }">${i }</option>
		</c:forEach>
		</select>
		
	<input type="hidden" name="action" value="applydiscount">
	<input type="submit" value="Apply Discounts">
	<input type="hidden" name="action=" value="cancel">
	<input type="submit" value="Cancel">
</form>
</body>
</html>