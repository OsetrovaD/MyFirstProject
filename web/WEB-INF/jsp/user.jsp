<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Страница пользователя</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px">Привет, ${sessionScope.user.login}</p>
    <p><a href="${pageContext.request.contextPath}/change-data">Изменить данные</a> | <a href=
        "${pageContext.request.contextPath}/orders">Мои заказы</a> | <a href=
        "${pageContext.request.contextPath}/comments?id=${sessionScope.user.id}&url=user">Мои комментарии</a></p>
    <c:if test="${sessionScope.user.role.name eq 'Администратор'}">
        <p><a href="${pageContext.request.contextPath}/add-game">Добавить игру</a> | <a href=
                    "${pageContext.request.contextPath}/all-users">Список всех пользователей</a> | <a href=
                    "${pageContext.request.contextPath}/game-search">Изменение статуса заказа</a></p>
    </c:if>
</body>
</html>
