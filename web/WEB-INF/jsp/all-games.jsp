<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Список всех игр</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px">Отсортировать по:</p>
<p><a href="${pageContext.request.contextPath}/all-games?sort=nameAsc">Названию(алфавитный порядок)</a> | <a href=
"${pageContext.request.contextPath}/all-games?sort=nameDesc">Названию(обратный порядок)</a> | <a href=
"${pageContext.request.contextPath}/all-games?sort=yearDesc">Год выпуска(по убыванию)</a> | <a href=
"${pageContext.request.contextPath}/all-games?sort=yearAsc">Год выпуска(по возрастанию)</a></p>
<table width="1200" border="1">
    <col width="200"><col width="950"><col width="50">
    <tr>
        <th align="left">Название</th>
        <th align="left">Описание</th>
        <th align="left">Год выпуска</th>
    </tr>
    <c:if test="${not empty requestScope.games}">
        <c:forEach var="game" items="${requestScope.games}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/game-info?id=${game.id}">${game.name}</a></td>
                <td>${game.description}</td>
                <td>${game.issueYear}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
