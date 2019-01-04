<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Подтверждение заказа</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Параметры заказа:</b></p>
<table border="1">
    <col width="200"><col width="200"><col width="50"><col width="50">
    <tr>
        <th align="left">Количество </th>
        <th align="left">Платформа</th>
        <th align="left">Цена</th>
        <th align="left">Количество</th>
    </tr>
    <c:forEach var="game" items="${sessionScope.basket.gamesInBasket}">
        <tr>
            <td><a href="${pageContext.request.contextPath}/game-info?id=${game.id}">${game.name}</a></td>
            <td>${game.platform.name}</td>
            <td align="center">${game.price}р.</td>
            <td align="center">${game.number}</td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4" align="right"><b>Итого: </b>${sessionScope.sum}р.</td>
    </tr>
</table>
<p><b>Способ оплаты: </b>${sessionScope.payment_form.name}</p>
<p><b>Способ доставки: </b>${sessionScope.delivery_method.name}(${sessionScope.delivery_method.price}р.)</p>
<p><b>Итоговая сумма заказа: </b>${sessionScope.delivery_method.price + sessionScope.sum}р.</p>
<form action="${pageContext.request.contextPath}/order-confirm" method="post">
    <input type="submit" value="Подтвердить заказ">
</form>
</body>
</html>
