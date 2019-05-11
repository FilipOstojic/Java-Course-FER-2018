<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

	<body  bgcolor="#E6E6FA">
		<h1>Sing Up page</h1>
		<br>
		<p>Register here:</p>
		
		<form action="<%=request.getContextPath()%>/servleti/save" method="POST">

		<div>
		 <div>
		  <span class="formLabel">First name</span><input type="text" name="firstName" value='<c:out value="${zapis.firstName}"/>' size="50">
		 </div>
		 <c:if test="${zapis.haveError('firstName')}">
		 <div class="error"><c:out value="${zapis.getError('firstName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Last name</span><input type="text" name="lastName" value='<c:out value="${zapis.lastName}"/>' size="50">
		 </div>
		 <c:if test="${zapis.haveError('lastName')}">
		 <div class="error"><c:out value="${zapis.getError('lastName')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">E-Mail</span><input type="text" name="email" value='<c:out value="${zapis.email}"/>' size="50">
		 </div>
		 <c:if test="${zapis.haveError('email')}">
		 <div class="error"><c:out value="${zapis.getError('email')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Nickname</span><input type="text" name="nickname" value='<c:out value="${zapis.nickname}"/>' size="50">
		 </div>
		 <c:if test="${zapis.haveError('nickname')}">
		 <div class="error"><c:out value="${zapis.getError('nickname')}"/></div>
		 </c:if>
		</div>

		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value="${zapis.password}"/>' size="50">
		 </div>
		 <c:if test="${zapis.haveError('password')}">
		 <div class="error"><c:out value="${zapis.getError('password')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Pohrani">
		  <input type="submit" name="metoda" value="Odustani">
		</div>
		
		</form>

	</body>
</html>
