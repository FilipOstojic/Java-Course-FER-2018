<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<body bgcolor="#E6E6FA">
		<p> Here is a list of entries by: <%=session.getAttribute("provided.user.nick")%> </p>
        <c:choose>
		   <c:when test="${entries==null}">
		     The list is empty!
		   </c:when>
		   <c:otherwise>
		      <c:if test="${!entries.isEmpty()}">
		         <ul>
		            <c:forEach var="e" items="${entries}">
		               <li>
		                  <div style="padding-left: 10px;">
		                     <a href= <%=request.getContextPath()%>/servleti/author/<%=session.getAttribute("provided.user.nick")%>/${e.id}>${e.title}</a>
		                  </div>
		               </li>
		            </c:forEach>
		         </ul>
		      </c:if>
		   </c:otherwise>
		</c:choose>
		<c:if test="${providedEqual}">
		<p>Provided ID matches with one that is currently logged in. You are allowed to add/change blog entries.</p>
		<p><a href= <%=request.getContextPath()%>/servleti/author/<%=session.getAttribute("provided.user.nick")%>/new>NEW</a></p>
		</c:if>
		<c:if test="${!providedEqual}">
		<p>Provided ID does not match with one that is currently logged in. You are not allowed to add/edit blog entries.</p>
		</c:if>
		<br><a href=" <%=request.getContextPath()%>/servleti/main">return to main page</a><br><br>
	</body>
</html>