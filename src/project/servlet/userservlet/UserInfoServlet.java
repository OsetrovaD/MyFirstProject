package project.servlet.userservlet;

import project.dto.userdto.UserDataDto;
import project.dto.userdto.UserDataForSession;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;

@WebServlet("/user-info")
public class UserInfoServlet extends HttpServlet {

    private static final String USER_INFO_PARAMETER = "userInfo";
    private static final String PAGE_NAME = "user-info";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataForSession user = (UserDataForSession) req.getSession().getAttribute(USER_FOR_SESSION_PARAMETER);
        UserDataDto info = UserService.getInstance().getInfo(user.getId());
        req.setAttribute(USER_INFO_PARAMETER, info);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
