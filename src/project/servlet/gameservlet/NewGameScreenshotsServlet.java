package project.servlet.gameservlet;

import project.dto.PlatformPriceDto;
import project.dto.StorageDto;
import project.dto.gamedto.NewGameDto;
import project.entity.DeveloperCompany;
import project.service.GameService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.ConstantUtil.SUCCESS_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SCREENSHOTS_PAGE;

@WebServlet("/new-game-screenshots")
public class NewGameScreenshotsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("new-game-screenshots")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] screenshots = req.getParameterValues("screenshot");
        NewGameDto gameInfo = (NewGameDto) req.getSession().getAttribute("game_info");
        DeveloperCompany developerCompany = (DeveloperCompany) req.getSession().getAttribute("developerCompany");
        String[] subgenres = (String[]) req.getSession().getAttribute("subgenres");
        PlatformPriceDto platforms = (PlatformPriceDto) req.getSession().getAttribute("platforms");
        StorageDto storage = (StorageDto) req.getSession().getAttribute("storage");

        boolean save = GameService.getInstance().save(gameInfo, developerCompany, subgenres, platforms.getPlatformPrice(), storage.getPlatformNumber(), screenshots);

        if (save) {
            req.getSession().removeAttribute("game_info");
            req.getSession().removeAttribute("developerCompany");
            req.getSession().removeAttribute("subgenres");
            req.getSession().removeAttribute("platforms");
            req.getSession().removeAttribute("storage");
            resp.sendRedirect(SUCCESS_PAGE);
        } else {
            resp.sendRedirect(NEW_GAME_SCREENSHOTS_PAGE);
        }
    }
}
