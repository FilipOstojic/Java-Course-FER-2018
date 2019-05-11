<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<html>
   <head>
      <title>Add new blog Entry</title>
   </head>
   <body bgcolor="#E6E6FA">
      <table>
         <tr>
            <td>
               <form action="<%=request.getContextPath()%>/servleti/newEntry" id="entryForm">
                  Title:<br> <input type="text" name="title"><br>
                  Content:<br> <textarea rows="4" cols="50" name="text"></textarea><br>
                  <br> <input type="submit" value="Submit">
               </form>
               
               <p>
               <a href=" <%=request.getContextPath()%>/servleti/main">return to main page</a>
               </p>
            </td>
         </tr>
      </table>
   </body>
</html>