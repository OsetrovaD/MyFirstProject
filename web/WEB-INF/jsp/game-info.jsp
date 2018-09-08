<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 08.09.2018
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Информация об игре</title>
</head>
<body>
<img src="${pageContext.request.contextPath}${requestScope.game.image}" align="left" vspace="15" hspace="25" width="250" alt="Game Poster">
    <p><b>Название:</b> ${requestScope.game.name}</p>
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
    <p><b>Цены:</b>
    <c:if test="${not empty requestScope.game.platformPrice}">
        <c:forEach var="platform_price" items="${requestScope.game.platformPrice}">
            <p>${platform_price}</p>
        </c:forEach>
    </c:if>
    <%--<c:if test="${not empty requestScope.game.comment}">--%>
        <%--<c:forEach var="comment" items="${requestScope.game.comment}">--%>
            <%--<p>${comment}</p>--%>
        <%--</c:forEach>--%>
    <%--</c:if>--%>
    <p><b>Скриншоты:</b>
    <c:if test="${not empty requestScope.game.screenshots}">
        <c:forEach var="screenshot" items="${requestScope.game.screenshots}">
            <img src="${pageContext.request.contextPath}${screenshot}" align="left" vspace="15" hspace="25" width="250" alt="screenshot">
        </c:forEach>
    </c:if>
</body>
</html>
