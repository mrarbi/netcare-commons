<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>
Login to Zombo com
</title>
<script language="javascript" type="text/javascript" src="<c:url value="/resources/javascript/login.js" />"></script>

<script language="javascript" type="text/javascript">
function load()
{
	if (navigator.appName.indexOf("Explorer") == -1)
	{
	   explorer = false;
	   plugin = navigator.mimeTypes["application/x-iid"];
	}
	else
	{
	   explorer = true;
	   plugin = ControlExists("IID.iIDCtl");
	}
	if (plugin)
	{
	   if (explorer)
		 document.writeln("<OBJECT NAME='iID' CLASSID='CLSID:5BF56AD2-E297-416E-BC49-00B327C4426E' WIDTH=0 HEIGHT=0></OBJECT>");
	   else
		 document.writeln("<OBJECT NAME='iID' TYPE='application/x-iid' WIDTH=0 HEIGHT=0></OBJECT>");
	}
	else
	{
	   document.writeln("Software not installed");
	}
}
load();
</script>

</head>


<body>
<p>
This is body of login
</p>


<p>
<input class="low" type="button" value="Log me in!" onclick="callLogin1()">
</p>

<p>
<input class="low" type="button" value="Logout!" onclick="callLogout()">
</p>
</body>
</html>