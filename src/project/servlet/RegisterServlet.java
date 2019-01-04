package project.servlet;

import project.dto.userdto.NewUserDto;
import project.dto.userdto.UserDataForSession;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.CommonRequestParameterConstantUtil.EMAIL_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.FIRST_NAME_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.LAST_NAME_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.LOGIN_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.PASSWORD_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.PHONE_NUMBER_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.TEXT_AREA_PARAMETER;
import static project.util.ConstantUtil.REGISTER_PAGE;
import static project.util.ConstantUtil.USER_PAGE;
import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;

@WebServlet("/register-page")
public class RegisterServlet extends HttpServlet {

    private static final String PAGE_NAME = "register";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewUserDto newUser = NewUserDto.builder()
                .login(req.getParameter(LOGIN_PARAMETER))
                .password(req.getParameter(PASSWORD_PARAMETER))
                .firstName(req.getParameter(FIRST_NAME_PARAMETER))
                .lastName(req.getParameter(LAST_NAME_PARAMETER))
                .email(req.getParameter(EMAIL_PARAMETER))
                .phoneNumber(req.getParameter(PHONE_NUMBER_PARAMETER))
                .address(req.getParameter(TEXT_AREA_PARAMETER))
                .build();

        Long id = UserService.getInstance().save(newUser);
        if (id != null) {
            UserDataForSession userData = UserService.getInstance().getById(id);
            req.getSession().setAttribute(USER_FOR_SESSION_PARAMETER, userData);
            resp.sendRedirect(USER_PAGE);
        } else {
            resp.sendRedirect(REGISTER_PAGE);
        }
    }
}
