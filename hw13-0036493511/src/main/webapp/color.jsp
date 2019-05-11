<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%

    String choosenColor = (String) session.getAttribute("pickedBgCol");
    String color;
    
    if (choosenColor == null) color = "#FFFFFF";
    else { 
    	color = choosenColor;
    }
    
%>

<html>
	<body bgcolor=<%=color%>>
		<a href="setColor?color=white">WHITE</a><br>
		<a href="setColor?color=red">RED</a><br>
		<a href="setColor?color=green">GREEN</a><br>
		<a href="setColor?color=cyan">CYAN</a><br><br>
		<a href="/webapp2">return</a>
	</body>
</html>