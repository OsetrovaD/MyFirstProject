package project.servlet.gameservlet;

import project.entity.Game;
import project.entity.Storage;
import project.service.GameService;
import project.service.StorageService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;

@WebServlet("/game-info")
public class GameFullInfoServlet extends HttpServlet {

    private static final String GAMES_PARAMETER = "game";
    private static final String STORAGE_PARAMETER = "storage";
    private static final String PAGE_NAME = "game-info";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter(ID_PARAMETER));
        Game game = GameService.getInstance().getById(id);
        List<Storage> storages = StorageService.getInstance().getByGameId(id);
        req.setAttribute(GAMES_PARAMETER, game);
        req.setAttribute(STORAGE_PARAMETER, storages);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
