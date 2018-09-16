<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Информация об игре</title>
    <style type="text/css">
        img {
            float: left;
            margin: 0 15px 5px 0;
        }
    </style>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
    <p style="padding-top: 20px"><img src="${pageContext.request.contextPath}${requestScope.game.image}" vspace="15" hspace="25" width="250" alt="Game Poster"><b>Название:</b> ${requestScope.game.name}</p>
    <p><b>Описание:</b> ${requestScope.game.description}</p>
    <p><b>Разработчик:</b> ${requestScope.game.companyDeveloper}</p>
    <p><b>Год выпуска:</b> ${requestScope.game.yearOfIssue}</p>
    <c:if test="${not empty requestScope.game.minimalSystemRequirements}">
        <p><b>Минимальные системные требования:</b> ${requestScope.game.minimalSystemRequirements}</p>
    </c:if>
    <c:if test="${not empty requestScope.game.recommendedSystemRequirements}">
        <p><b>Рекомендуемые системные требования:</b> ${requestScope.game.recommendedSystemRequirements}</p>
    </c:if>
    <p><b>Возрастное ограничение:</b> ${requestScope.game.ageLimit.name}</p>
    <p><b>Жанры:</b>
    <c:if test="${not empty requestScope.game.genre}">
        <c:forEach var="genre" items="${requestScope.game.genre}">
            <p>${genre}</p>
        </c:forEach>
    </c:if>
    <p><b>Поджанры:</b>
    <c:if test="${not empty requestScope.game.subgenre}">
        <c:forEach var="subgenre" items="${requestScope.game.subgenre}">
            <p>${subgenre}</p>
        </c:forEach>
    </c:if>
    <p><b>Цены:</b></p>
    <c:if test="${not empty requestScope.game.platformPrice}">
        <table border="1">
            <col width="120"><col width="50"><col width="150">
        <c:forEach var="platform_price" items="${requestScope.game.platformPrice}">
            <tr>
                <td>${platform_price.key.name}</td>
                <td align="center">${platform_price.value} р</td>
                <td><a href="${pageContext.request.contextPath}/add-to-basket?id=${requestScope.game.id}&name=${requestScope.game.name}&platform=${platform_price.key}&price=${platform_price.value}">Положить в корзину</a></td>
            </tr>
        </c:forEach>
        </table>
    </c:if>
    <p><a href="${pageContext.request.contextPath}/comments?id=${requestScope.game.id}&url=game-info">Комментарии</a></p>
    <p><a href="${pageContext.request.contextPath}/add-comment?id=${requestScope.game.id}">Добавить комментарий</a></p>

    <c:if test="${not empty requestScope.game.screenshots}">
        <p><b>Скриншоты:</b>
        <c:forEach var="screenshot" items="${requestScope.game.screenshots}">
            <img src="${pageContext.request.contextPath}${screenshot}" align="left" vspace="15" hspace="25" width="250" alt="screenshot">
        </c:forEach>
    </c:if></p>
</body>
</html>
