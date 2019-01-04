<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Мои заказы</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px"><b>Заказы</b></p>
    <table border="1">
        <col width="7"><col width="120"><col width="120"><col width="120"><col width="90"><col width="90"><col width="120"><col width="120"><col width="15"><col width="50">
        <tr>
            <th>Номер заказа</th>
            <th>Способ доставки</th>
            <th>Способ оплаты</th>
            <th>Состояние</th>
            <th>Дата заказа</th>
            <th>Дата доставки</th>
            <th colspan="4">Предметы в заказе</th>
        </tr>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td rowspan="${fn:length(order.items)+ 1}" align="center">${order.id}</td>
                <td rowspan="${fn:length(order.items)+ 1}" align="center">${order.deliveryMethod.name}(${order.deliveryMethod.price})p.</td>
                <td rowspan="${fn:length(order.items)+ 1}" align="center">${order.paymentForm.name}</td>
                <td rowspan="${fn:length(order.items)+ 1}" align="center">${order.condition.name}</td>
                <td rowspan="${fn:length(order.items)+ 1}" align="center">${order.date}</td>
                <td rowspan="${fn:length(order.items)+ 1}" align="center">${order.deliveryDate}</td>
                <th>Название игры</th>
                <th>Платформа</th>
                <th>Цена</th>
                <th>Количество</th>
            </tr>
            <c:set var="total" value="${0}"/>
            <c:forEach var="item" items="${order.items}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/game-info?id=${item.gameId}">${item.gameName}</a></td>
                    <td align="center">${item.platform.name}</td>
                    <td align="center">${item.price}</td>
                    <td align="center">${item.number}</td>
                </tr>
                <c:set var="total" value="${total + (item.price * item.number)}" />
            </c:forEach>
            <c:set var="total" value="${total + order.deliveryMethod.price}" />
            <tr>
                <td colspan="10" align="right"><b>Итого: </b> ${total}р.</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
