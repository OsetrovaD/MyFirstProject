package project.servlet.gameservlet;

import project.entity.Game;
import project.service.GameService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game-info")
public class GameFullInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Game game = GameService.getInstance().getById(id);
        req.setAttribute("game", game);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("game-info")).forward(req, resp);
    }
}
