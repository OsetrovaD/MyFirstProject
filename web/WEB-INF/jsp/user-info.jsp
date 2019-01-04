<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Информация о пользователе</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px"><b>Мои данные:</b></p>
    <table border="1">
        <col width="150"><col width="200">
        <tr>
            <td><b>Имя</b></td>
            <td>${requestScope.userInfo.firstName}</td>
            <td><a href="${pageContext.request.contextPath}/change-first-name">Изменить</a></td>
        </tr>
        <tr>
            <td><b>Фамилия</b></td>
            <td>${requestScope.userInfo.lastName}</td>
            <td><a href="${pageContext.request.contextPath}/change-last-name">Изменить</a></td>
        </tr>
        <tr>
            <td><b>E-mail</b></td>
            <td>${requestScope.userInfo.email}</td>
            <td><a href="${pageContext.request.contextPath}/change-email">Изменить</a></td>
        </tr>
        <tr>
            <td><b>Номер телефона</b></td>
            <td>${requestScope.userInfo.phoneNumber}</td>
            <td><a href="${pageContext.request.contextPath}/change-phone-number">Изменить</a></td>
        </tr>
        <tr>
            <td><b>Адрес</b></td>
            <td>${requestScope.userInfo.address}</td>
            <td><a href="${pageContext.request.contextPath}/change-address">Изменить</a></td>
        </tr>
    </table>
</body>
</html>
