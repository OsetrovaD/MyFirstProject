<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Результаты поиска</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Заказы:</b></p>
    <table border="1">
        <col width="7"><col width="100"><col width="150"><col width="150"><col width="150"><col width="100"><col width="100"><col width="150">
        <tr>
            <th>Номер</th>
            <th>Пользователь</th>
            <th>Состояние</th>
            <th>Способ оплаты</th>
            <th>Способ доставки</th>
            <th>Дата оформления</th>
            <th>Дата доставки</th>
            <th>Изменить данные</th>
        </tr>
        <c:forEach var="order" items="${requestScope.ordersList}">
            <tr>
                <td>${order.id}</td>
                <td>${order.user.login}</td>
                <td>${order.condition.name}</td>
                <td>${order.paymentForm.name}</td>
                <td>${order.deliveryMethod.name}</td>
                <td>${order.date}</td>
                <td>${order.deliveryDate}</td>
                <td><a href="${pageContext.request.contextPath}/change-order-data?id=${order.id}">Изменить данные</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
