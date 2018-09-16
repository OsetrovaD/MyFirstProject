package project.servlet.gameservlet;

import project.dto.PlatformPriceDto;
import project.dto.StorageDto;
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

@WebServlet("/new-game-platforms")
public class NewGamePlatformsServlet extends HttpServlet {

    private static final String POSTFIX = "_1";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("platforms", GamePlatformService.getInstance().getAll());
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("new-game-platforms")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StorageDto tempStorage = StorageDto.builder()
                .platformNumber(new HashMap<>())
                .build();
        PlatformPriceDto tempPlatformPrice = PlatformPriceDto.builder()
                .platformPrice(new HashMap<>())
                .build();

        if (req.getParameterValues("game_platforms") == null || areValuesEmpty(req)) {
            resp.sendRedirect(NEW_GAME_PLATFORMS_PAGE);
        } else {
            Arrays.stream(req.getParameterValues("game_platforms")).forEach(t -> tempPlatformPrice.getPlatformPrice().put(GamePlatform.getByName(t), Integer.valueOf(req.getParameter(t))));
            Arrays.stream(req.getParameterValues("game_platforms")).forEach(t -> tempStorage.getPlatformNumber().put(GamePlatform.getByName(t), Short.valueOf(req.getParameter(t + POSTFIX))));
            req.getSession().setAttribute("platforms", tempPlatformPrice);
            req.getSession().setAttribute("storage", tempStorage);
            resp.sendRedirect(NEW_GAME_SCREENSHOTS_PAGE);
        }
    }

    private boolean areValuesEmpty(HttpServletRequest req) {
        String[] gamePlatforms = req.getParameterValues("game_platforms");
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
