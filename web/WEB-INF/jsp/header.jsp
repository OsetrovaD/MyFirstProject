<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
</style>
    <ul class="nav">
        <li><a class="menu" href="${pageContext.request.contextPath}/user">Домой</a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/all-games?sort=standard">Все игры</a></li>
        <li><a class="menu" href="${pageContext.request.contextPath}/game-search">Поиск игр</a></li>
        <c:if test="${not empty sessionScope.basket}">
        <li><a class="menu" href="${pageContext.request.contextPath}/basket">Оформить заказ</a></li>
        </c:if>
    </ul>
    <form class="exit" action="${pageContext.request.contextPath}/logout" method="get">
        <input type="submit" align="right" value="Выход">
</form>