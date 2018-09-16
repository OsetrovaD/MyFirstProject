package project.servlet.gameservlet;

import project.entity.Subgenre;
import project.service.SubgenreService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.MappingForAdminConst.NEW_GAME_PLATFORMS_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SUBGENRE_PAGE;

@WebServlet("/new-game-subgenre")
public class NewGameSubgenreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Subgenre> subgenres = SubgenreService.getInstance().getAll();
        req.setAttribute("subgenres", subgenres);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("new-game-subgenre")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterValues("subgenre") == null) {
            resp.sendRedirect(NEW_GAME_SUBGENRE_PAGE);
        } else {
            req.getSession().setAttribute("subgenres", req.getParameterValues("subgenre"));
            resp.sendRedirect(NEW_GAME_PLATFORMS_PAGE);
        }
    }
}
