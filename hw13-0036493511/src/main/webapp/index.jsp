<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%

    String choosenColor = (String) session.getAttribute("pickedBgCol");
    String color;
    
    if (choosenColor == null) color = "#FFFFFF";
    else { 
    	color = choosenColor;
    }
    
%>

<html>
	<body bgcolor=<%=color%>>
		<h1>13th java homework!</h1>
		<p>
			<a href="color.jsp">Background Color Chooser</a>
		</p>
		<br>
		<form action="trigonometric" method="GET">
 			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br><br>
			<input type="submit" value="Tabeliraj"> <input type="reset" value="Reset">
		</form>
		<p>
			<a href="stories/funny.jsp">Wanna hear a joke?</a>
		</p>
		<p>
			<a href="report.jsp">OS usage report</a>
		</p>
		<form action="powers" method="GET">
 			Parametar a:<br><input type="number" name="a" min="-100" max="100" step="1" value="10"><br>
 			Parametar b:<br><input type="number" name="b" min="-100" max="100" step="1" value="20"><br>
			Power n:<br><input type="number" name="n" min="1" max="5" step="1" value="1"><br><br>
			<input type="submit" value="Create XLS"> <input type="reset" value="Reset">
		</form>
		<p>
			<a href="appinfo.jsp">App info</a>
		</p>
		<p>
			<a href="glasanje">Vote for your favourite band!</a>
		</p>
	</body>
</html>
		