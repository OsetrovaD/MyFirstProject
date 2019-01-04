<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b><fmt:message key="login.autorize"/></b></p>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" id="login_01" name="login" size="30" required>
        <label for="login_01"><span>*</span><fmt:message key="login.username"/></label><br>
        <input type="password" id="pass_01" name="password" size="30" required>
        <label for="pass_01"><span>*</span><fmt:message key="login.pass"/></label><br>
        <input type="submit" value="<fmt:message key="login.button"/>">
    </form>
<p><a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="button.back"/></a>
</body>
</html>
