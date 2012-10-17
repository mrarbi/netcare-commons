<%--

    Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="resourcePath" required="true" %>
<%@ attribute name="contextPath" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- mvk:header -->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${title}</title>
	
	<link rel="icon" href="<c:url value="/favicon.ico" />" type="image/vnd.microsoft.icon"/>
	<link rel="stylesheet" href="${contextPath}${resourcePath}/css/bootstrap-2.2.1.min.css" />
	<link rel="stylesheet" href="${contextPath}${resourcePath}/css/jquery-ui-1.8.24.custom.css" />
	<link rel="stylesheet" href="${contextPath}${resourcePath}/css/mvk-ui.css" />
	
	<script type="text/javascript">
		var GLOB_CTX_PATH = '${contextPath}';
	</script>
	<c:set var="GLOB_CTX_PATH" value="${contextPath}" />
	
	<script type="text/javascript" src="${contextPath}${resourcePath}/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${contextPath}${resourcePath}/js/jquery-ui-1.8.24.custom.min.js"></script>
	<script type="text/javascript" src="${contextPath}${resourcePath}/js/bootstrap-2.2.1.min.js"></script>
	
	<script type="text/javascript" src="${contextPath}${resourcePath}/js/mvk-ui.js"></script>
	<script type="text/javascript">
	
		$(function() {
			MVK.MENU.init();
		});
	
	</script>
	
	<jsp:doBody />
</head>
<!-- mvk:header / -->
