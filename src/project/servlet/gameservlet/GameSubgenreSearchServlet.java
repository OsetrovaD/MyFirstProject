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

@WebServlet("/by-subgenre-search")
public class GameSubgenreSearchServlet extends HttpServlet {

    private static final String PAGE_NAME = "search-by-subgenre";
    private static final String SUBGENRES_PARAMETER = "subgenres";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Subgenre> subgenres = SubgenreService.getInstance().getAll();
        req.setAttribute(SUBGENRES_PARAMETER, subgenres);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
