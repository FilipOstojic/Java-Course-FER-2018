<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
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
   <body bgcolor=<%=color %>>
      <h1>Glasanje za omiljeni bend:</h1>
      <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
      <ol>
        <c:forEach var="bandEntry" items="${band}">
        	<li><a href="glasanje-glasaj?id=${bandEntry.id}">${bandEntry.name}</a></li>
        </c:forEach>
      </ol>
   </body>
</html>