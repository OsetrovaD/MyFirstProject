<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="locale.jsp"%>
<style type="text/css">
    ul {
        list-style: none;
        margin: 0;
        padding-left: 0;
        width: 70%;
        float: left;
    }
    li {position: relative;
        display: inline;
    }
    .menu {
        display: inline-block;
        width: 7em;
        padding:10px;
        background-color: #f4f4f4;
        border: 1px solid #333333;
        text-decoration: none;
        color: #333333;
        text-align: center;
    }
    .exit {
        float: right;
        margin: 0;
        padding-left: 0;
    }
    div {
        float: right;
        margin: 0;
        padding-right: 80px;
    }
</style>
<c:if test="${not empty sessionScope.user.role}">
    <ul class="nav">
        <li><a class="menu" href="${pageContext.request.contextPath}/user"><fmt:message key="header.home"/></a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/all-games?sort=standard"><fmt:message key="header.allgames"/></a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/game-search"><fmt:message key="header.gamesearch"/></a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/user-info"><fmt:message key="header.userinfo"/></a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/comments?id=${sessionScope.user.id}&url=user"><fmt:message key="header.comments"/></a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/orders"><fmt:message key="header.userorders"/></a></li>
        <c:if test="${sessionScope.user.role.name eq 'Администратор'}">
            <li><a class="menu" href="${pageContext.request.contextPath}/all-orders"><fmt:message key="header.allorders"/></a></li>
        </c:if>
        <c:if test="${not empty sessionScope.basket and fn:length(sessionScope.basket.gamesInBasket) > 0}">
            <li><a class="menu" href="${pageContext.request.contextPath}/basket"><fmt:message key="header.ordering"/></a></li>
        </c:if>
    </ul>
    <form class="exit" action="${pageContext.request.contextPath}/logout" method="get">
        <input type="submit" align="right" value="<fmt:message key="header.logout"/>">
    </form>
</c:if>
<div>
    <a href="${pageContext.request.contextPath}/change-locale?language=ru_RU">rus</a> | <a href="${pageContext.request.contextPath}/change-locale?language=en_US">eng</a>
</div>
