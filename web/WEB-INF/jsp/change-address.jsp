<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение адреса</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px"><b>Введите адрес:</b></p>
    <form action="${pageContext.request.contextPath}/change-data" method="post">
        <input type="hidden" name="change" value="address">
        <textarea  name="dataChanges" required></textarea>
        <input type="submit" value="Изменить">
    </form>
</body>
</html>
