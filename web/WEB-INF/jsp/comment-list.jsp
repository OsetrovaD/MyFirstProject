<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Комментарии</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<table width="1200" style="padding-top: 20px">
    <col width="200"><col width="800"><col width="200">
    <c:if test="${requestScope.url eq 'user'}">
            <tr>
                <th align="left">Название игры</th>
                <th align="left">Комментарий</th>
                <th align="left">Дата добавления</th>
            </tr>
        <c:forEach var="comment" items="${requestScope.comments}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/game-info?id=${comment.game.id}">${comment.game.name}</a></td>
                <td>${comment.text}</td>
                <td>${comment.date}</td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${requestScope.url eq 'game-info'}">
            <tr>
                <th align="left">Пользователь</th>
                <th align="left">Комментарий</th>
                <th align="left">Дата добавления</th>
            </tr>
        <c:forEach var="comment" items="${requestScope.comments}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/comments?id=${comment.user.id}&url=user">${comment.user.login}</a></td>
                <td>${comment.text}</td>
                <td>${comment.date}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
