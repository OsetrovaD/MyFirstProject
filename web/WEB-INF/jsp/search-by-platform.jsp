<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Поиск по игровой платформе</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Список платформ:</b></p>
    <form action="${pageContext.request.contextPath}/search-result?url=/by-platform-search" method="post">
        <c:forEach var="platform" items="${requestScope.platforms}">
            <input type="radio" name="values" value="${platform.name}" id="game_platform"><label for="game_platform">${platform.name}</label><br>
        </c:forEach>
        <input type="submit" value="Выбрать">
    </form>
</body>
</html>
