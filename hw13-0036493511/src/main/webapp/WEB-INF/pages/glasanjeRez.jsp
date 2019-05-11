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
      <style type="text/css">
         table.rez td {text-align: center;}
      </style>
   </head>
   <body bgcolor=<%=color%>>
      <h1>Rezultati glasanja</h1>
      <p>Ovo su rezultati glasanja.</p>
      <table border="1" cellspacing="0" class="rez">
         <thead>
            <tr>
               <th>Bend</th><th>Broj glasova</th>
            </tr>
         </thead>
         <tbody>
            <c:forEach var="result" items="${results}">
        	<tr>
               <td>${result.name}</td><td>${result.noOfVotes}</td>
            </tr>
       		</c:forEach>
         </tbody>
      </table>
      <h2>Grafički prikaz rezultata</h2>
      <img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
      <h2>Rezultati u XLS formatu</h2>
      <p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
      <h2>Razno</h2>
      <p>Primjeri pjesama pobjedničkih bendova:</p>
      <ul>
      	<c:forEach var="linkAndBand" items="${links}">
        	<li><a href=${linkAndBand.link } target="_blank">${linkAndBand.name}</a></li>
        </c:forEach>
      </ul>
      <p>
      	<a href="/webapp2">Return to homePage</a>
      </p>
   </body>
</html>