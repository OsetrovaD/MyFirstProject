package project.servlet;

import project.entity.enumonly.Role;
import project.service.RoleService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;
import static project.util.ConstantUtil.SUCCESS_PAGE;

@WebServlet("/change-role")
public class ChangeRoleServlet extends HttpServlet {

    private static final String ROLES_PARAMETER = "roles";
    private static final String ROLE_PARAMETER = "role";
    private static final String PAGE_NAME = "change-role";
    private static final String CHANGE_ROLE_ID_URL = "/change-role?id=";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ID_PARAMETER, req.getParameter(ID_PARAMETER));
        req.setAttribute(ROLES_PARAMETER, RoleService.getInstance().getAll());
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = RoleService.getInstance()
                .updateRole(Long.valueOf(req.getParameter(ID_PARAMETER)), Role.getByName(req.getParameter(ROLE_PARAMETER)));

        if (result) {
            resp.sendRedirect(SUCCESS_PAGE);
        } else {
            resp.sendRedirect(CHANGE_ROLE_ID_URL + req.getParameter(ID_PARAMETER));
        }
    }
}
