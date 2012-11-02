<%--

       Copyright 2011,2012 Callista Enterprise AB

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

--%>
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