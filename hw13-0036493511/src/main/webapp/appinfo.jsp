<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
	String choosenColor = (String) session.getAttribute("pickedBgCol");
    String color;
    
    if (choosenColor == null) color = "#FFFFFF";
    else { 
    	color = choosenColor;
    }
	
	long currentTime = System.currentTimeMillis();
	long startTime = (long)request.getServletContext().getAttribute("timeStarted");
	long timePassed =  currentTime - startTime;
	
	long miliseconds = timePassed % 1000;
	long days = timePassed / (1000*60*60*24);
	long hours = (timePassed - days*(1000*60*60*24)) / (1000*60*60);
	long minutes = (timePassed - days*(1000*60*60*24) - hours*(1000*60*60)) / (1000*60);
	long seconds = (timePassed - days*(1000*60*60*24) - hours*(1000*60*60) - minutes*(1000*60)) / 1000;
    
%>


<html>
	<body bgcolor=<%=color%>>
		<h2>
			The time elapsed since the launch of the web application:
		</h2>
		<p>
			
			Days: <%=days%>, Hours: <%=hours%>, Minutes: <%=minutes%>, Seconds: <%=seconds%>, Miliseconds: <%=miliseconds%>
			 
		</p>
		<a href="/webapp2">return</a>
	</body>
</html>