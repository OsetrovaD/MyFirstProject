<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Количество</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Какое количество добавляем?</b></p>
<table border="1">
    <col width="150"><col width="100"><col width="50">
    <tr>
        <th>Платформа</th>
        <th>Добавляемое количество</th>
    </tr>
    <tr>
        <td align="center">${sessionScope.storageChange.platform.name}</td>
        <td align="center">
            <form action="${pageContext.request.contextPath}/add-to-storage" method="post">
                <input type="text" name="number" size="3" pattern="[0-9]">
                <input type="submit" value="Добавить">
            </form>
        </td>
    </tr>
</table>
</body>
</html>
