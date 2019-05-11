<%@page import="hr.fer.zemris.java.hw15.web.servlets.AuthorServlet"%>
<%@ page import="hr.fer.zemris.java.hw15.model.BlogComment" %>
<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
   <body bgcolor="#E6E6FA">
      <%
         BlogEntry entry = (BlogEntry) session.getAttribute("current.entry");
         int size = entry.getComments()==null? 0: entry.getComments().size();
         long entryID = (long) entry.getId();
         Boolean equal = (Boolean) session.getAttribute("providedEqual");
             
         %>
      <table>
         <tr>
            <td>
               <h2><%=entry.getTitle()%>
               </h2>
               <p><%=entry.getText()%>
               </p>
               <br>
               <%if (equal) {%>
               <p><a href= <%=request.getContextPath()%>/servleti/author/<%=session.getAttribute("provided.user.nick")%>/edit?entryID=<%=entryID%>>EDIT</a></p>
               <%}%>
               <table>
                  <p>Blog entry comments:</p>
                  <%if (size == 0) {%>
                  This blog entry has no posts.
                  <%}%>
                  <%for (BlogComment comment : entry.getComments()) {%>
                  <tr>
                     <td>
                        <%=comment.getMessage()%><br>
                        <i>posted by: <%=comment.getUsersEMail()%><br>
                        posted on: <%=comment.getPostedOn().toString()%><br><br>
                        </i>
                     </td>
                  </tr>
                  <%}%>
               </table>
               <br>
               <h3>Add Comment</h3>
               <form action="<%=request.getContextPath()%>/servleti/addComment" method="post">
                  <textarea rows="4" cols="50" name="message"></textarea>
                  <input type="hidden" name="entryID" value=<%=entryID%>><br>
                  <input type="submit" value="Submit">
               </form>
            </td>
         </tr>
      </table>
      <br><br><a href=" <%=request.getContextPath()%>/servleti/main">return to main page</a><br>
   </body>
</html>