<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%

    String choosenColor = (String) session.getAttribute("pickedBgCol");
    String color;
    
    if (choosenColor == null) color = "#FFFFFF";
    else { 
    	color = choosenColor;
    }
    
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset='utf-8'>
	</head>
	
	<body bgcolor=<%=color%>>
		<h1> Page with trigonometric values!</h1>
		<p> Results are displayed below:</p>
		
		<table>
			<thead>
				<tr><th>Num</th><th>Sinus</th><th>Cosinus</th></tr>
			</thead>
			<tbody>
			<c:forEach var="entry" items="${table}">
				<tr><td>${entry.num}</td><td>${entry.sin}</td><td>${entry.cos}</td></tr>
			</c:forEach>
			</tbody>
		</table>
	<br>
	<a href="/webapp2">return</a>
	</body>
	
</html>