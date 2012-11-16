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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="style" required="false" %>
<c:if test="${style == null}"><c:set var="style" value="padding-right: 40px;" /></c:if>
<!-- mvk:touch-item -->
	<div class="containerBoxShadow paperSlip">
		<div class="top">
			<div class="wrap"></div>
			<div class="left"></div>
			<div class="right"></div>
		</div>
		<div class="wrap">
			<div class="boxShadowBody" style="${style}">
				<div class="listItemBody">
					<jsp:doBody />
				</div>
			</div>
		</div>
		<div class="bottom">
			<div class="wrap"></div>
			<div class="left"></div>
			<div class="right"></div>
		</div>
	</div>
<!-- mvk:touch-item / -->
