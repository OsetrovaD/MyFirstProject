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

import static project.util.CommonRequestParameterConstantUtil.LOGIN_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.PASSWORD_PARAMETER;
import static project.util.ConstantUtil.USER_PAGE;
import static project.util.MappingForAdminConst.LOGIN_PAGE;
import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String PAGE_NAME = "login-page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = UserDto.builder()
                .login(req.getParameter(LOGIN_PARAMETER))
                .password(req.getParameter(PASSWORD_PARAMETER))
                .build();
        UserDataForSession userData = UserService.getInstance().getByLoginAndPassword(userDto);

        if (userData != null) {
            req.getSession().setAttribute(USER_FOR_SESSION_PARAMETER, userData);
            resp.sendRedirect(USER_PAGE);
        } else {
            resp.sendRedirect(LOGIN_PAGE);
        }
    }
}
