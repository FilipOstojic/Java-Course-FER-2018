<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Error</title>
	</head>

	<body bgcolor="#E6E6FA">
		<h1>error ocured</h1>
		<p><c:out value="${message}"/></p>

		<p><a href="<%=request.getContextPath()%>/servleti/main">return to Main page</a></p>
	</body>
</html>