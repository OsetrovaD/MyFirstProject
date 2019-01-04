<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить данные заказа</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <br>
    <p style="padding-top: 20px"><b>Показать заказы в период</b></p>
    <form action="${pageContext.request.contextPath}/order-search-result" method="post">
        <table>
            <tr>
                <th>с <input type="date" name="start_date"></th>
                <th>по <input type="date" name="finish_date"></th>
            </tr>
        </table>
        <input type="submit" value="Выбрать">
    </form>
</body>
</html>
