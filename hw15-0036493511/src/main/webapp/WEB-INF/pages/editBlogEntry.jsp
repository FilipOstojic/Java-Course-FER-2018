<%@ page import="hr.fer.zemris.java.hw15.dao.DAOProvider" %>
<%@ page import="hr.fer.zemris.java.hw15.model.BlogEntry" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%
    Long id = Long.valueOf(request.getParameter("entryID"));
    BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
%>

<html>
	<body bgcolor="#E6E6FA">
		<table>
		    <tr>
		        <td>
		            <form action="<%=request.getContextPath()%>/servleti/editEntry?<%=id%>" method="post">
		                Title: <br><input type="text" name="title" value="<%=entry.getTitle()%>"><br>
		                Content: <br><textarea rows="4" cols="50" name="text"><%=entry.getText()%></textarea><br>
		                <input type="hidden" name="entryID" value=<%=id%>>
		                <br><input type="submit" value="Submit">
		            </form>
		            <br><a href=" <%=request.getContextPath()%>/servleti/main">return to main page</a><br><br>
		        </td>
			</tr>
		</table>
	</body>
</html>
