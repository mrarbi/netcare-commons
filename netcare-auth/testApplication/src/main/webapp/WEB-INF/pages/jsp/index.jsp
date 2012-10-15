<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.ui.ModelMap" %>
<html>
<head>
<title>
Welcome to Zombo com
</title>
</head>


	<body>
		<p>
			This is body of index
		</p>
		<p>
			username is:
		   ${username}
		</p>
		<p>
			Name for user is:
		   ${name}
		</p>
		<p>
			Is user a doctor?:
		   ${isDoctor}
		</p>
		<p>
			How many care units it is associated to:
		   ${careUnits}
		</p>
		<p>
			details are:
		   ${details}
		</p>
		   
	
	</body>
</html>