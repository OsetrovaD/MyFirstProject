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

import static project.util.ConstantUtil.ADD_GAME;
import static project.util.ConstantUtil.DEVELOPER_COMPANY;

@WebServlet("/add-game")
public class AddGameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AgeLimit> ageLimits = AgeLimitService.getInstance().getAll();
        req.setAttribute("ageLimits", ageLimits);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("add-game")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        NewGameDto newGame = NewGameDto.builder()
                .name(req.getParameter("game_name"))
                .description(req.getParameter("description"))
                .minimalSystemRequirements(req.getParameter("min_sys_req"))
                .recommendedSystemRequirements(req.getParameter("max_sys_req"))
                .yearOfIssue(Integer.valueOf(req.getParameter("issue_year")))
                .image(req.getParameter("poster"))
                .ageLimit(AgeLimit.getByName(req.getParameter("age_limit")))
                .build();

        if (newGame != null) {
            req.getSession().setAttribute("game_info", newGame);
            resp.sendRedirect(DEVELOPER_COMPANY);
        } else {
            resp.sendRedirect(ADD_GAME);
        }
    }
}
