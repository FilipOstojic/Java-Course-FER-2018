<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.Random" %>

<%
	
	String[] colors = new String[]{"grey", "red", "orange", "black", "blue", "green", "yellow"};
	Random rand = new Random();
	String c = colors[rand.nextInt(7)];
    
%>

<%

    String choosenColor = (String) session.getAttribute("pickedBgCol");
    String color;
    
    if (choosenColor == null) color = "#FFFFFF";
    else { 
    	color = choosenColor;
    }
    
%>

<html>
	<body bgcolor=<%=color %>>
		<p><font color=<%=c%>>
			Q: Why do Java developers wear glasses? <br>
			A: Because they don't C#!
		</font></p>
		<br><br>
		<a href="/webapp2">return</a>
	</body>
</html>