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
<%@ attribute name="menuText" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${menuText == null}">
	<c:set var="menuText" value="Meny" />
</c:if>

<div id="menuContainer">
	<div id="leftMenu">
		<div class="wrapper">
			<div class="listWrapper">
				<h2 class="assistiveObject">Fasta tjänster</h2>
				<ul id="permanent">
					<jsp:doBody />
				</ul>
			</div>
		</div>
		<div class="topBorder"></div>
		<div class="bottomBorder"></div>
		<a class="toggleMenuButton onlyOnNarrow bold">${menuText}</a>
	</div>
</div>
		
