package project.servlet.gameservlet;

import project.entity.Genre;
import project.service.GenreService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/by-genre-search")
public class GameGenreSearchServlet extends HttpServlet {

    private static final String PAGE_NAME = "search-by-genre";
    private static final String GENRES_PARAMETER = "genres";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Genre> genres = GenreService.getInstance().getAll();
        req.setAttribute(GENRES_PARAMETER, genres);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
