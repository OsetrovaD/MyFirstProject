<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Результаты поиска</title>
</head>
<body>
    <c:if test="${not empty requestScope.games}">
        <c:forEach var="game" items="${requestScope.games}">
            <a href="${pageContext.request.contextPath}/game-info?id=${game.id}"><p>${game.name}</p></a>
        </c:forEach>
    </c:if>
</body>
</html>
