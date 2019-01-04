package project.servlet.gameservlet;

import project.dto.gamedto.NewGameDto;
import project.entity.enumonly.AgeLimit;
import project.service.AgeLimitService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.ConstantUtil.ADD_GAME_PAGE;
import static project.util.ConstantUtil.DEVELOPER_COMPANY_PAGE;
import static project.util.SessionParameterConstantUtil.GAME_INFO;

@WebServlet("/add-game")
public class AddGameServlet extends HttpServlet {

    private static final String NEW_GAME_NAME = "game_name";
    private static final String NEW_GAME_DESCRIPTION = "description";
    private static final String NEW_GAME_MIN_SYS_REQ = "min_sys_req";
    private static final String NEW_GAME_MAX_SYS_REQ = "max_sys_req";
    private static final String NEW_GAME_ISSUE_YEAR = "issue_year";
    private static final String NEW_GAME_POSTER = "poster";
    private static final String NEW_GAME_AGE_LIMIT = "age_limit";
    private static final String AGE_LIMITS = "ageLimits";
    private static final String PAGE_NAME = "add-game";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AgeLimit> ageLimits = AgeLimitService.getInstance().getAll();
        req.setAttribute(AGE_LIMITS, ageLimits);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        NewGameDto newGame = NewGameDto.builder()
                .name(req.getParameter(NEW_GAME_NAME))
                .description(req.getParameter(NEW_GAME_DESCRIPTION))
                .minimalSystemRequirements(req.getParameter(NEW_GAME_MIN_SYS_REQ))
                .recommendedSystemRequirements(req.getParameter(NEW_GAME_MAX_SYS_REQ))
                .yearOfIssue(Integer.valueOf(req.getParameter(NEW_GAME_ISSUE_YEAR)))
                .image(req.getParameter(NEW_GAME_POSTER))
                .ageLimit(AgeLimit.getByName(req.getParameter(NEW_GAME_AGE_LIMIT)))
                .build();

        if (newGame != null) {
            if (req.getSession().getAttribute(GAME_INFO) != null) {
                req.getSession().removeAttribute(GAME_INFO);
            }
            req.getSession().setAttribute(GAME_INFO, newGame);
            resp.sendRedirect(DEVELOPER_COMPANY_PAGE);

        } else {
            resp.sendRedirect(ADD_GAME_PAGE);
        }
    }
}
