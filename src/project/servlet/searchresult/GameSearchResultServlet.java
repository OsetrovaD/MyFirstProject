package project.servlet.searchresult;

import project.dto.gamedto.GameNameDto;
import project.entity.Game;
import project.service.GameService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static project.util.CommonRequestParameterConstantUtil.GAMES_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.URL_PARAMETER;
import static project.util.ConstantUtil.BY_ISSUE_YEAR_SEARCH;
import static project.util.ConstantUtil.BY_NAME_SEARCH;
import static project.util.ConstantUtil.NO_RESULT_PAGE;

@WebServlet("/search-result")
public class GameSearchResultServlet extends HttpServlet {

    private static final String VALUES_PARAMETER = "values";
    private static final String PAGE_NAME = "search-result";
    private static final String GAME_INFO_ID_URL = "/game-info?id=";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameNameDto> games = new ArrayList<>();
        Game game = null;
        switch (req.getParameter(URL_PARAMETER)) {
            case BY_ISSUE_YEAR_SEARCH:
                games = GameService.getInstance().getByIssueYear(Integer.valueOf(req.getParameter(VALUES_PARAMETER)));
                break;
            case BY_NAME_SEARCH:
                game = GameService.getInstance().getByName(req.getParameter(VALUES_PARAMETER));
                break;
            default:
                games = GameService.getInstance().getByCharacteristic(req.getParameter(VALUES_PARAMETER), req.getParameter(URL_PARAMETER));
                break;
        }

        if (game == null && games.size() == 0) {
            resp.sendRedirect(NO_RESULT_PAGE);
        } else if (game != null) {
            resp.sendRedirect(GAME_INFO_ID_URL + game.getId());
        } else {
            req.setAttribute(GAMES_PARAMETER, games);
            getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
        }
    }
}
