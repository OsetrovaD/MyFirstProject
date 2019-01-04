package project.servlet.gameservlet;

import project.dto.gamedto.GameNameDescriptionYearDto;
import project.service.GameService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.util.QuerySortUtil.BY_NAME_ASC;
import static project.util.QuerySortUtil.BY_NAME_DESC;
import static project.util.QuerySortUtil.BY_YEAR_ASC;
import static project.util.QuerySortUtil.BY_YEAR_DESC;

@WebServlet("/all-games")
public class AllGameServlet extends HttpServlet {

    private static final String NAME_DESC_PARAMETER = "nameDesc";
    private static final String NAME_ASC_PARAMETER = "nameAsc";
    private static final String YEAR_DESC_PARAMETER = "yearDesc";
    private static final String YEAR_ASC_PARAMETER = "yearAsc";
    private static final String STANDARD = "standard";
    private static final Map<String, String> SORTING = new HashMap<>();
    private static final String STANDARD_VALUE = "";
    private static final String SORT_REQUEST_PARAMETER = "sort";
    private static final String GAMES_PARAMETER = "games";
    private static final String PAGE_NAME = "all-games";

    static {
        SORTING.put(STANDARD, STANDARD_VALUE);
        SORTING.put(NAME_DESC_PARAMETER, BY_NAME_DESC);
        SORTING.put(NAME_ASC_PARAMETER, BY_NAME_ASC);
        SORTING.put(YEAR_DESC_PARAMETER, BY_YEAR_DESC);
        SORTING.put(YEAR_ASC_PARAMETER, BY_YEAR_ASC);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter(SORT_REQUEST_PARAMETER);
        List<GameNameDescriptionYearDto> allGames = GameService.getInstance().getAll(SORTING.get(parameter));
        req.setAttribute(GAMES_PARAMETER, allGames);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
