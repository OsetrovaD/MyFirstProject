<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Оформление заказа</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Товары в корзине:</b></p>
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
            <td align="center">${game.number}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
