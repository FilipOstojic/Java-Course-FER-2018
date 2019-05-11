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
		<h2>OS usage</h2>
		<p>Here are the results of the OS usage in a survey that we completed:</p>
		<img src="<%=request.getContextPath()%>/reportImage"/>
		<br><br>
		<a href="/webapp2">return</a>
	</body>
</html>