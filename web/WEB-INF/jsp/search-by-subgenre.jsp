<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Поиск по поджанру</title>
</head>
<body>
<p>Поджанр: </p>
<form method="post" action="${pageContext.request.contextPath}/search-result?url=/by-subgenre-search">
    <c:forEach var="subgenre" items="${requestScope.subgenres}">
        <input type="radio" name="values" value="${subgenre.name}" id="genres"><label for="genres">${subgenre.name}</label><br>
    </c:forEach>
    <input type="submit" value="Выбрать">
</form>
<p><a href="${pageContext.request.contextPath}/game-search">Назад</a> | <a href="${pageContext.request.contextPath}/user">Домой</a></p>
</body>
</html>
