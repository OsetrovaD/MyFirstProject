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
import static project.util.SessionParameterConstantUtil.SUBGENRES;

@WebServlet("/new-game-subgenre")
public class NewGameSubgenreServlet extends HttpServlet {

    private static final String PAGE_NAME = "new-game-subgenre";
    private static final String SUBGENRES_PARAMETER = "subgenres";
    private static final String CURRENT_SUBGENRE_PARAMETER = "subgenre";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Subgenre> subgenres = SubgenreService.getInstance().getAll();
        req.setAttribute(SUBGENRES_PARAMETER, subgenres);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterValues(CURRENT_SUBGENRE_PARAMETER) == null) {
            resp.sendRedirect(NEW_GAME_SUBGENRE_PAGE);
        } else {
            if (req.getSession().getAttribute(SUBGENRES) != null) {
                req.getSession().removeAttribute(SUBGENRES);
            }
            req.getSession().setAttribute(SUBGENRES, req.getParameterValues(CURRENT_SUBGENRE_PARAMETER));
            resp.sendRedirect(NEW_GAME_PLATFORMS_PAGE);
        }
    }
}
