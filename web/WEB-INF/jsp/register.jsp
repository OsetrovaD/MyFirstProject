<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<p>Введите данные:</p>
<form action="${pageContext.request.contextPath}/register-page" method="post">
    <input type="text" id="login_01" name="login" pattern="^[A-Za-z0-9]+$" placeholder="Только буквы латинского алфавита и цифры" size="60" required>
    <label for="login_01"><span>*</span>Логин</label><br>
    <input type="password" id="pass_01" name="password" pattern="^[A-Za-z0-9]+$" placeholder="Только буквы латинского алфавита и цифры" size="60" required>
    <label for="pass_01"><span>*</span>Пароль</label><br>
    <input type="text" id="first_name_01" name="first_name" size="60">
    <label for="first_name_01">Имя</label><br>
    <input type="text" id="last_name_01" name="last_name" size="60">
    <label for="last_name_01">Фамилия</label><br>
    <input type="text" id="email_01" name="email" size="60" required>
    <label for="email_01"><span>*</span>E-mail</label><br>
    <input type="text" id="phone_number_01" pattern="\d{11}" placeholder="80290000000" name="phone_number" size="30">
    <label for="phone_number_01">Номер телефона</label><br>
    <textarea name="text_area" placeholder="Адрес"></textarea><br>
    <input type="submit" value="Зарегистрироваться">
</form>
<p><a href="${pageContext.request.contextPath}/index.jsp">Назад</a>
</body>
</html>
