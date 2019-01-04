<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Страница пользователя</title>
    <style type="text/css">
        .facts {
            border: 1px solid #333333;
            background-color: #f4f4f4;
            color: #333333;
            width: 700px;
            margin-right: 27%;
            padding: 0;
            text-align: center;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px"><fmt:message key="userpage.greeting"/>, ${sessionScope.user.login}</p>
    <p><a href="${pageContext.request.contextPath}/change-data">Изменить все данные</a></p>
    <c:if test="${sessionScope.user.role.name eq 'Администратор'}">
        <p><a href="${pageContext.request.contextPath}/add-game">Добавить игру</a> | <a href=
                    "${pageContext.request.contextPath}/all-users">Список всех пользователей</a> | <a href=
                    "${pageContext.request.contextPath}/choose-date-period">Поиск заказа по дате</a> | <a href=
                    "${pageContext.request.contextPath}/add-to-storage">Изменить количество товара на складе</a></p>
    </c:if>
    <br>
    <p><a href="${pageContext.request.contextPath}/download">Скачать прайс-лист</a></p>
    <div class="facts">
        <h3>Интересный факт: </h3>
        <p>${requestScope.interestingFact}</p>
    </div>
</body>
</html>
