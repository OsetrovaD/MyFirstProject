<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Поджанры</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px">Выберите поджанры:</p>
<form action="${pageContext.request.contextPath}/new-game-subgenre" method="post">
    <c:forEach var="game_subgenre" items="${requestScope.subgenres}">
        <label>
            <input type="checkbox" name="subgenre" value="${game_subgenre.id}">
        </label>${game_subgenre.name}<br>
    </c:forEach>
    <input type="submit" value="Выбрать">
</form>
</body>
</html>
