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

@WebServlet("/search-result")
public class GameSearchResultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameNameDto> games = null;
        Game game = null;
        switch (req.getParameter("url")) {
            case "/by-issue-year-search":
                games = GameService.getInstance().getByIssueYear(Integer.valueOf(req.getParameter("values")));
                break;
            case "/by-name-search":
                game = GameService.getInstance().getByName(req.getParameter("values"));
                break;
            default:
                games = GameService.getInstance().getByCharacteristic(req.getParameter("values"), req.getParameter("url"));
                break;
        }

        if (game == null && games == null) {
            resp.sendRedirect("/no-result");
        } else if (game != null) {
            resp.sendRedirect("/game-info?id=" + game.getId());
        } else {
            req.setAttribute("games", games);
            getServletContext().getRequestDispatcher(JspPathUtil.getPath("search-result")).forward(req, resp);
        }
    }
}
