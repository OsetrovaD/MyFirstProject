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

import static project.util.ConstantUtil.SUCCESS_PAGE;

@WebServlet("/change-role")
public class ChangeRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("roles", RoleService.getInstance().getAll());
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("change-role")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = RoleService.getInstance()
                .updateRole(Long.valueOf(req.getParameter("id")), Role.getByName(req.getParameter("role")));

        if (result) {
            resp.sendRedirect(SUCCESS_PAGE);
        } else {
            resp.sendRedirect("/change-role?id=" + req.getParameter("id"));
        }
    }
}
