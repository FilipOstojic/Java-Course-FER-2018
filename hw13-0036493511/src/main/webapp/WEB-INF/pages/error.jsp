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
	<body bgcolor=<%=color%>>
		<h2>ERROR occured.</h2>
		<p>The entered parameters are incorrect. Make sure the arguments are not empty. 
		</p>
		<br><br>
		<a href="/webapp2">return</a>
	</body>
</html>