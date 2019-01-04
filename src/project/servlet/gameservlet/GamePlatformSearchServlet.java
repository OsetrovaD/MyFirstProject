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

    private static final String PAGE_NAME = "search-by-platform";
    private static final String PLATFORMS_PARAMETER = "platforms";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GamePlatform> platforms = GamePlatformService.getInstance().getAll();
        req.setAttribute(PLATFORMS_PARAMETER, platforms);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
