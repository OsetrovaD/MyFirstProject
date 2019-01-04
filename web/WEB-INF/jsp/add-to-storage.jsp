<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Добавить на склад</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
    <p style="padding-top: 20px"><b>Список игр:</b></p>
<c:forEach var="game" items="${requestScope.games}">
    <a href="${pageContext.request.contextPath}/game-info?id=${game.id}">${game.name}</a><br>
    <table border="1">
        <col width="200"><col width="200">
        <tr>
            <th>Платформы</th>
            <th>Добавить на склад</th>
        </tr>
            <c:forEach var="platform" items="${game.platforms}">
                <tr>
                    <td>${platform.name}</td>
                    <td align="center"><a href="${pageContext.request.contextPath}/set-number?game_id=${game.id}&platform=${platform}">Добавить на склад</a></td>
                </tr>
            </c:forEach>
    </table>
    <br>
</c:forEach>
</body>
</html>
