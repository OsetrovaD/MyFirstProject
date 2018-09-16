<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Поиск по жанру</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px">Жанр: </p>
    <form method="post" action="${pageContext.request.contextPath}/search-result?url=/by-genre-search">
        <c:forEach var="genre" items="${requestScope.genres}">
            <input type="radio" name="values" value="${genre.name}" id="genres"><label for="genres">${genre.name}</label><br>
        </c:forEach>
        <input type="submit" value="Выбрать">
    </form>
</body>
</html>
