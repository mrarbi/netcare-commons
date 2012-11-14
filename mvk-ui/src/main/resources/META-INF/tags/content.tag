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
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" isELIgnored="false" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="backTitle" required="false" %>
<%@ attribute name="backUrl" required="false" %>
<%@ attribute name="backToWhat" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="contentContainer">
	<c:if test="${not empty title}">
		<h1>${title}</h1>
	</c:if>
	
	<c:if test="${not empty backTitle and not empty backUrl and not empty backToWhat}">
		<a href="${backUrl}" class="backButton">
			${backTitle} <span>${backToWhat}</span>
		</a>
	</c:if>
	
	<div id="inboxDetailWrapperTop">
		<div class="wrapper"></div>
		<div class="left"></div>
		<div class="right"></div>
	</div>
	
	<div id="inboxDetailWrapper">
		<div class="wrapper">
		<div id="maincontainerwrapper">
			<div class="row-fluid">
				<div class="span12">
					<jsp:doBody />
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	</div>
	
	<div id="inboxDetailWrapperBottom">
		<div class="wrapper"></div>
		<div class="left"></div>
		<div class="right"></div>
	</div>
	
</div>
<div class="clear"></div>
		
