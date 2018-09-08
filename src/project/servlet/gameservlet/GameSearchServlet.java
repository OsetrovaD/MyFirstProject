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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("game-search")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(getSearchVariants(req.getParameter("radio_group")));
    }

    private String getSearchVariants(String parameter) {
        return setSearchVariants().get(parameter);
    }

    private Map<String, String> setSearchVariants() {
        Map<String, String> references = new HashMap<>();
        references.put(BY_NAME, BY_NAME_SEARCH);
        references.put(BY_PLATFORM, BY_PLATFORM_SEARCH);
        references.put(BY_GENRE, BY_GENRE_SEARCH);
        references.put(BY_SUBGENRE, BY_SUBGENRE_SEARCH);
        references.put(BY_ISSUE_YEAR, BY_ISSUE_YEAR_SEARCH);
        return references;
    }
}
