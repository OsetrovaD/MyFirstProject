<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Корзина</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<form id="form" action="${pageContext.request.contextPath}/basket" method="post" style="padding-top: 50px"></form>
<table border="1">
    <col width="200"><col width="200"><col width="50"><col width="50">
    <tr>
        <th align="left">Название</th>
        <th align="left">Платформа</th>
        <th align="left">Цена</th>
        <th align="left">Количество</th>
    </tr>
        <c:forEach var="game" items="${sessionScope.basket.gamesInBasket}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/game-info?id=${game.id}">${game.name}</a></td>
                <td>${game.platform.name}</td>
                <td>${game.price}</td>
                <td align="center">
                    <input form="form" type="text" size="4" placeholder="${game.number}" name="number">
                </td>
            </tr>
        </c:forEach>
</table>
<br>
<input form="form" type="submit" value="Продолжить оформление заказа"><br>
<a href="${requestScope.url}">Назад</a>
</body>
</html>
