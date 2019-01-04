<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Скриншоты</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Проверьте правильность данных, введённых на предыдущей странице</b></p>
<table>
    <col width="100"><col width="7">
    <tr>
        <th>Платформа</th>
        <th>Цена</th>
    </tr>

<c:forEach var="price" items="${sessionScope.platforms.platformPrice}">
    <tr>
        <td>${price.key.name}</td>
        <td align="center">${price.value}</td>
    </tr>
</c:forEach>

</table>
<br>
<table>
    <col width="100"><col width="7">
    <tr>
        <th>Платформа</th>
        <th>На склад</th>
    </tr>
    <c:forEach var="number" items="${sessionScope.storage.platformNumber}">
        <tr>
            <td>${number.key.name}</td>
            <td align="center">${number.value}</td>
        </tr>
    </c:forEach>

</table>
<br>
<p><b>Добавьте ссылки на скриншоты (не более 3):</b></p>
<form action="${pageContext.request.contextPath}/new-game-screenshots" method="post">
    <input type="text" name="screenshot" size="100"><br>
    <input type="text" name="screenshot" size="100"><br>
    <input type="text" name="screenshot" size="100"><br>
    <input type="submit" value="Сохранить игру">
</form>
</body>
</html>
