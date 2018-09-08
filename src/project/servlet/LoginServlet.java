package project.servlet;

import project.dto.userdto.UserDataForSession;
import project.dto.userdto.UserDto;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("login-page")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = UserDto.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();
        UserDataForSession userData = UserService.getInstance().getByLoginAndPassword(userDto);

        if (userData != null) {
            req.getSession().setAttribute("user", userData);
            resp.sendRedirect("/user");
        } else {
            resp.sendRedirect("/login");
        }
    }
}
