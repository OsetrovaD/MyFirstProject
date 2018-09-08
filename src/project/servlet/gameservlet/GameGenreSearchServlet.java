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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Genre> genres = GenreService.getInstance().getAll();
        req.setAttribute("genres", genres);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("search-by-genre")).forward(req, resp);
    }
}
