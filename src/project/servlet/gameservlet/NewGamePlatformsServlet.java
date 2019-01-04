package project.servlet.gameservlet;

import project.dto.PlatformPriceDto;
import project.dto.storagedto.NewGameStorageDto;
import project.entity.enumonly.GamePlatform;
import project.service.GamePlatformService;
import project.util.JspPathUtil;
import project.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static project.util.MappingForAdminConst.NEW_GAME_PLATFORMS_PAGE;
import static project.util.MappingForAdminConst.NEW_GAME_SCREENSHOTS_PAGE;
import static project.util.SessionParameterConstantUtil.PLATFORMS;
import static project.util.SessionParameterConstantUtil.STORAGE;

@WebServlet("/new-game-platforms")
public class NewGamePlatformsServlet extends HttpServlet {

    private static final String POSTFIX = "_1";
    private static final String PLATFORMS_PARAMETER = "platforms";
    private static final String PAGE_NAME = "new-game-platforms";
    private static final String GAME_PLATFORMS_PARAMETER = "game_platforms";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(PLATFORMS_PARAMETER, GamePlatformService.getInstance().getAll());
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewGameStorageDto tempStorage = NewGameStorageDto.builder()
                .platformNumber(new HashMap<>())
                .build();
        PlatformPriceDto tempPlatformPrice = PlatformPriceDto.builder()
                .platformPrice(new HashMap<>())
                .build();

        if (req.getParameterValues(GAME_PLATFORMS_PARAMETER) == null || areValuesEmpty(req)) {
            resp.sendRedirect(NEW_GAME_PLATFORMS_PAGE);
        } else {
            if (req.getSession().getAttribute(PLATFORMS) != null && req.getSession().getAttribute(STORAGE) != null) {
                req.getSession().removeAttribute(PLATFORMS);
                req.getSession().removeAttribute(STORAGE);
            }
            Arrays.stream(req.getParameterValues(GAME_PLATFORMS_PARAMETER))
                    .forEach(t -> tempPlatformPrice.getPlatformPrice().put(GamePlatform.getByName(t), Integer.valueOf(req.getParameter(t))));
            Arrays.stream(req.getParameterValues(GAME_PLATFORMS_PARAMETER))
                    .forEach(t -> tempStorage.getPlatformNumber().put(GamePlatform.getByName(t), Short.valueOf(req.getParameter(t + POSTFIX))));
            req.getSession().setAttribute(PLATFORMS, tempPlatformPrice);
            req.getSession().setAttribute(STORAGE, tempStorage);
            resp.sendRedirect(NEW_GAME_SCREENSHOTS_PAGE);
        }
    }

    private boolean areValuesEmpty(HttpServletRequest req) {
        String[] gamePlatforms = req.getParameterValues(GAME_PLATFORMS_PARAMETER);
        boolean result = false;

        for (String gamePlatform : gamePlatforms) {
            if (StringUtil.isEmpty(req.getParameter(gamePlatform)) || StringUtil.isEmpty(req.getParameter(gamePlatform + POSTFIX))) {
                result = true;
                break;
            }
        }

        return result;
    }
}
