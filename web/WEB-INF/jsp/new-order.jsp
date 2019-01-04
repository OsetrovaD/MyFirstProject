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
            <td align="center">${game.price}р.</td>
            <td align="center">${game.number}</td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4" align="right"><b>Итого: </b>${sessionScope.sum}р.</td>
    </tr>
</table>
<form action="${pageContext.request.contextPath}/new-order" method="post">
    <p><b>Выберите способ оплаты:</b></p>
    <c:forEach var="form" items="${requestScope.paymentForm}">
        <input type="radio" name="payment_form" id = "group_1" value="${form}"><label for="group_1">${form.name}</label>
    </c:forEach>
    
    <p><b>Выберите способ доставки:</b></p>
    <c:forEach var="method" items="${requestScope.deliveryMethod}">
        <input type="radio" name="delivery_method" id = "group_2" value="${method}"><label for="group_2">${method.name}(${method.price}р.)</label>
    </c:forEach>
    <br>
    <input type="submit" value="Оформить заказ">
</form>
</body>
</html>
