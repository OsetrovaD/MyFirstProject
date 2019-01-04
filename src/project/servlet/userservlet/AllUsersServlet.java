package project.servlet.userservlet;

import project.dto.userdto.UserForAdminDto;
import project.service.RoleService;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/all-users")
public class AllUsersServlet extends HttpServlet {

    private static final String USERS_PARAMETER = "users";
    private static final String PAGE_NAME = "all-users";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserForAdminDto> users = UserService.getInstance().getAll();
        req.setAttribute(USERS_PARAMETER, users);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
