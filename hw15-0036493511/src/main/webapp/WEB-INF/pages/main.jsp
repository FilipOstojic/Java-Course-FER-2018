<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<html>
	<head>
		<style type="text/css">
		.error {
		   font-family: fantasy;
		   font-weight: normal;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
           font-weight: bold;
		   text-align: right;
           padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>
   <body bgcolor="#E6E6FA">
      <h1>Main Blog page</h1>
      <br>
      <p align="center">
          <% if (session.getAttribute("current.user.id") != null) { %>
          Hello, <i><%= session.getAttribute("current.user.nickname")%>
      </i><br>
          <a href=<%=request.getContextPath()%>/servleti/logout>Logout</a>
          <% } else { %>
          You are currently not logged in!
          <% } %>
      </p>
      
      <% if (session.getAttribute("current.user.id") == null) { %>
      <p>Log in here: 
      <form action="<%=request.getContextPath()%>/servleti/login" method="POST">
  
        <div>
		 <div>
		  <span class="formLabel">Nickname</span><input type="text" name="nickname" value='<c:out value="${zapisL.nickname}"/>'size="50">
		 </div>
		 <c:if test="${zapisL.haveError('nickname')}">
		 <div class="error"><c:out value="${zapisL.getError('nickname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" size="50">
		 </div>
		 <c:if test="${zapisL.haveError('password')}">
		 <div class="error"><c:out value="${zapisL.getError('password')}"/></div>
		 </c:if>
		</div>
		
         <input type="submit" value="Log in"> <input type="reset" value="Reset">
      </form>
      <% } else { } %>
      
      <br>
      <p>
         Create a New Account: <a href="<%=request.getContextPath()%>/servleti/register">Sing Up</a>
      </p>
      <br>
      <p> Here is a list of currently registered users: </p>
        <c:choose>
		   <c:when test="${users==null}">
		     The list is empty!
		   </c:when>
		   <c:otherwise>
		      <c:if test="${!users.isEmpty()}">
		         <ul>
		            <c:forEach var="e" items="${users}">
		               <li>
		                  <div style="font-weight: bold">
		                     <c:out value="${e.lastName}"/>
		                     <c:out value="${e.firstName}"/>
		                  </div>
		                  <div style="padding-left: 10px;">
		                     <a href= <%=request.getContextPath()%>/servleti/author/${e.nickname}>${e.nickname}</a>
		                  </div>
		               </li>
		            </c:forEach>
		         </ul>
		      </c:if>
		   </c:otherwise>
		</c:choose>
   </body>
</html>