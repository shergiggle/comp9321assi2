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
City: <select name="city">
			<option value="Sydney">Sydney</option>
			<option value="Melbourne">Melbourne</option>
			<option value="Brisbane">Brisbane</option>
			<option value="Adelaid">Adelaide</option>
			<option value="Hobart">Hobart</option>
		</select><br>
Hotel:<input type="text" name="hotel"><br>
Room Type:<select name="type">
				<option value="SINGLE">SINGLE</option>
				<option value="DOUBLE">DOUBLE</option>
				<option value="QUEEN">QUEEN</option>
				<option value="EXECUTIVE">EXECUTIVE</option>
				<option value="SUITE">SUITE</option>
			</select><br>
Start: Date<select name="startdate">
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
End: date<select name="enddate">
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
		
New Room Amount:<input type="number" name="amount"><br><br>
		
	<input type="hidden" name="action" value="applydiscount">
	<input type="submit" value="Apply Discounts">
</form>
<form action="owner" method="POST">	
	<input type="hidden" name="action=" value="cancel">
	<input type="submit" value="Cancel">
</form>
</body>
</html>