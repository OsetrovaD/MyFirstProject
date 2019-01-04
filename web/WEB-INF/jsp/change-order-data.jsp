<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Изменить данные заказов</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Установите необходимые параметры:</b></p>
<form action="${pageContext.request.contextPath}/change-order-data" method="post">
    <label>Выберите состояние:
        <select name="order_condition">
            <c:forEach var="condition" items="${requestScope.conditions}">
                <option value="${condition}">${condition.name}</option>
            </c:forEach>
            <option value="default" selected>Состояние</option>
        </select>
    </label><br>
    <label>Установите дату доставки:
        <input type="date" name="delivery_date">
    </label><br>
    <input type="submit" value="Изменить">
</form>
</body>
</html>
