<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Игровые платформы</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Выберите платформы:</b></p>
<form action="${pageContext.request.contextPath}/new-game-platforms" method="post">
<table width="500">
    <col width="200"><col width="150"><col width="150">
    <tr>
        <th align="left"><b>Платформа</b></th>
        <th align="left"><b>Цена</b></th>
        <th align="left"><b>На склад (шт.)</b></th>
    </tr>
    <c:forEach var="platform" items="${requestScope.platforms}">
        <tr>
            <td><input type="checkbox" name="game_platforms" value="${platform.name}">${platform.name}</td>
            <td><input type="text" name="${platform.name}"></td>
            <td><input type="text" name="${platform.name}_1"></td>
        </tr>
    </c:forEach>
</table>
    <input type="submit" value="Отправить">
</form>
</body>
</html>
