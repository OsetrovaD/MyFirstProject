package project.servlet.gameservlet;

import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static project.util.ConstantUtil.BY_GENRE;
import static project.util.ConstantUtil.BY_GENRE_SEARCH;
import static project.util.ConstantUtil.BY_ISSUE_YEAR;
import static project.util.ConstantUtil.BY_ISSUE_YEAR_SEARCH;
import static project.util.ConstantUtil.BY_NAME;
import static project.util.ConstantUtil.BY_NAME_SEARCH;
import static project.util.ConstantUtil.BY_PLATFORM;
import static project.util.ConstantUtil.BY_PLATFORM_SEARCH;
import static project.util.ConstantUtil.BY_SUBGENRE;
import static project.util.ConstantUtil.BY_SUBGENRE_SEARCH;

@WebServlet("/game-search")
public class GameSearchServlet extends HttpServlet {

    private static final Map<String, String> SEARCH_QUERIES = new HashMap<>();
    private static final String SEARCH_PARAMETER = "radio_group";
    private static final String PAGE_NAME = "game-search";

    static {
        SEARCH_QUERIES.put(BY_NAME, BY_NAME_SEARCH);
        SEARCH_QUERIES.put(BY_PLATFORM, BY_PLATFORM_SEARCH);
        SEARCH_QUERIES.put(BY_GENRE, BY_GENRE_SEARCH);
        SEARCH_QUERIES.put(BY_SUBGENRE, BY_SUBGENRE_SEARCH);
        SEARCH_QUERIES.put(BY_ISSUE_YEAR, BY_ISSUE_YEAR_SEARCH);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(SEARCH_QUERIES.get(req.getParameter(SEARCH_PARAMETER)));
    }
}
