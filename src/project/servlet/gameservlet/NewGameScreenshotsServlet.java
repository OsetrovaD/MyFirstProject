package project.servlet.gameservlet;

import project.dto.PlatformPriceDto;
import project.dto.storagedto.NewGameStorageDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static project.util.ConstantUtil.SUCCESS_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SCREENSHOTS_PAGE;
import static project.util.SessionParameterConstantUtil.DEVELOPER_COMPANY;
import static project.util.SessionParameterConstantUtil.GAME_INFO;
import static project.util.SessionParameterConstantUtil.PLATFORMS;
import static project.util.SessionParameterConstantUtil.STORAGE;
import static project.util.SessionParameterConstantUtil.SUBGENRES;

@WebServlet("/new-game-screenshots")
public class NewGameScreenshotsServlet extends HttpServlet {

    private static final String PAGE_NAME = "new-game-screenshots";
    private static final String SCREENSHOT_PARAMETER = "screenshot";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] screenshots = req.getParameterValues(SCREENSHOT_PARAMETER);
        List<String> currentScreenshots = filledFields(screenshots);
        boolean save;

        if (currentScreenshots.size() > 0) {
            NewGameDto gameInfo = (NewGameDto) req.getSession().getAttribute(GAME_INFO);
            DeveloperCompany developerCompany = (DeveloperCompany) req.getSession().getAttribute(DEVELOPER_COMPANY);
            String[] subgenres = (String[]) req.getSession().getAttribute(SUBGENRES);
            PlatformPriceDto platforms = (PlatformPriceDto) req.getSession().getAttribute(PLATFORMS);
            NewGameStorageDto storage = (NewGameStorageDto) req.getSession().getAttribute(STORAGE);
            save = GameService.getInstance().save(gameInfo, developerCompany, subgenres, platforms.getPlatformPrice(), storage.getPlatformNumber(), currentScreenshots);

            if (save) {
                req.getSession().removeAttribute(GAME_INFO);
                req.getSession().removeAttribute(DEVELOPER_COMPANY);
                req.getSession().removeAttribute(SUBGENRES);
                req.getSession().removeAttribute(PLATFORMS);
                req.getSession().removeAttribute(STORAGE);
                resp.sendRedirect(SUCCESS_PAGE);
            } else {
                resp.sendRedirect(NEW_GAME_SCREENSHOTS_PAGE);
            }
        } else {
            resp.sendRedirect(NEW_GAME_SCREENSHOTS_PAGE);
        }
    }

    private List<String> filledFields(String[] screenshots) {
        return Arrays.stream(screenshots).filter(screenshot -> !screenshot.isEmpty()).collect(Collectors.toList());
    }
}
