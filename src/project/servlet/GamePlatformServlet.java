package project.servlet;

import project.entity.enumonly.GamePlatform;
import project.service.GamePlatformService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static project.util.SessionParameterConstantUtil.PLATFORMS;

@WebServlet("/gameplatform")
public class GamePlatformServlet extends HttpServlet {

    private static final String PAGE_NAME = "search-by-platform";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GamePlatform> platforms = GamePlatformService.getInstance().getAll();
        req.setAttribute(PLATFORMS, platforms);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
