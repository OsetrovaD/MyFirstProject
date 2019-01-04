<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<table width="1200" style="padding-top: 20px">
    <col width="150"><col width="150"><col width="150"><col width="150"><col width="150"><col width="150"><col width="150"><col width="150">
    <c:if test="${not empty requestScope.users}">
        <tr>
            <th align="left">Логин</th>
            <th align="left">Имя</th>
            <th align="left">Фамилия</th>
            <th align="left">e-mail</th>
            <th align="left">Адрес</th>
            <th align="left">Роль</th>
            <th align="left">Номер телефона</th>
            <th align="left">Изменить роль</th>
        </tr>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/comments?id=${user.id}&url=user">${user.login}</a></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.address}</td>
                <td>${user.role.name}</td>
                <td>${user.phoneNumber}</td>
                <td>
                    <c:if test="${user.id ne sessionScope.user.id and user.id ne 1}">
                    <a href="${pageContext.request.contextPath}/change-role?id=${user.id}">Изменить роль пользователя</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
