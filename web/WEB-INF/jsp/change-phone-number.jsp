<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение номера телефона</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px"><b>Введите номер телефона:</b></p>
    <form action="${pageContext.request.contextPath}/change-data" method="post">
        <input type="hidden" name="change" value="phoneNumber">
        <input type="text" name="dataChanges" required>
        <input type="submit" value="Изменить">
    </form>
</body>
</html>
