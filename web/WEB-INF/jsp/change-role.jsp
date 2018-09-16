<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Изменить роль пользователя</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
    <form action="${pageContext.request.contextPath}/change-role?id=${requestScope.id}" method="post" style="padding-top: 50px">
        <select name="role" >
            <c:forEach var="user_role" items="${requestScope.roles}">
                <option value="${user_role.name}">${user_role.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Выбрать">
    </form>
</body>
</html>
