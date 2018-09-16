<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Результаты поиска</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Результаты поиска:</b></p>
    <c:if test="${not empty requestScope.games}">
        <c:forEach var="game" items="${requestScope.games}">
            <p><a href="${pageContext.request.contextPath}/game-info?id=${game.id}">${game.name}</a></p>
        </c:forEach>
    </c:if>
</body>
</html>
