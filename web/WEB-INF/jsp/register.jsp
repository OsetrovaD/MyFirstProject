<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b><fmt:message key="registerpage.enterdata"/>:</b></p>
<form action="${pageContext.request.contextPath}/register-page" method="post">
    <input type="text" id="login_01" name="login" pattern="^[A-Za-z0-9]+$" placeholder="<fmt:message key="registerpage.namepattern"/>" size="60" required>
    <label for="login_01"><span>*</span><fmt:message key="registerpage.username"/></label><br>
    <input type="password" id="pass_01" name="password" pattern="^[A-Za-z0-9]+$" placeholder="<fmt:message key="registerpage.namepattern"/>" size="60" required>
    <label for="pass_01"><span>*</span><fmt:message key="registerpage.pass"/></label><br>
    <input type="text" id="first_name_01" name="first_name" size="60">
    <label for="first_name_01"><fmt:message key="registerpage.fname"/></label><br>
    <input type="text" id="last_name_01" name="last_name" size="60">
    <label for="last_name_01"><fmt:message key="registerpage.lname"/></label><br>
    <input type="text" id="email_01" name="email" size="60" required>
    <label for="email_01"><span>*</span>E-mail</label><br>
    <input type="text" id="phone_number_01" pattern="\d{11}" placeholder="80290000000" name="phone_number" size="30">
    <label for="phone_number_01"><fmt:message key="registerpage.telnumber"/></label><br>
    <textarea name="text_area" placeholder="<fmt:message key="registerpage.addresspattern"/>"></textarea><br>
    <input type="submit" value="<fmt:message key="registerpage.button"/>">
</form>
<p><a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="button.back"/></a>
</body>
</html>
