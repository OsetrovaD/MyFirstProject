package project.servlet.gameservlet;

import project.entity.enumonly.GamePlatform;
import project.service.GamePlatformService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/by-platform-search")
public class GamePlatformSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GamePlatform> platforms = GamePlatformService.getInstance().getAll();
        req.setAttribute("platforms", platforms);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("search-by-platform")).forward(req, resp);
    }
}
