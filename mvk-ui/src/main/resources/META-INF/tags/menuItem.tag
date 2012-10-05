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
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" isELIgnored="false" %>
<%@ attribute name="active" required="false" type="java.lang.Boolean" %>
<%@ attribute name="url" required="false" %>
<%@ attribute name="label" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${active == null}">
	<c:set var="active" value="false" />
</c:if>

<c:if test="${url == null}">
	<c:set var="url" value="#" />
</c:if>

<c:choose>
	<c:when test="${active}">
		<li class="active">
			<a href="${url}">
				<h3>${label}</h3>
			</a>
		</li>
	</c:when>
	<c:otherwise>
		<li>
			<a href="${url}">
				<h3>${label}</h3>
			</a>
		</li>
	</c:otherwise>
</c:choose>

		
