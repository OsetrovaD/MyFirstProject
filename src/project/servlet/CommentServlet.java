package project.servlet;

import project.dto.commentdto.GameCommentDto;
import project.dto.commentdto.UserCommentDto;
import project.service.CommentService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.URL_PARAMETER;
import static project.util.ConstantUtil.NO_RESULT_PAGE;
import static project.util.SessionParameterConstantUtil.GAME_INFO;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

    private static final String COMMENTS_PARAMETER = "comments";
    private static final String CURRENT_GAME_INFO_PARAMETER = "game-info";
    private static final String PAGE_NAME = "comment-list";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameCommentDto> gameComments = new ArrayList<>();
        List<UserCommentDto> userComments = new ArrayList<>();

        if (req.getParameter(URL_PARAMETER).equals(CURRENT_GAME_INFO_PARAMETER)) {
            gameComments = CommentService.getInstance().getByGameId(Long.valueOf(req.getParameter(ID_PARAMETER)));
            req.setAttribute(COMMENTS_PARAMETER, gameComments);
        } else {
            userComments = CommentService.getInstance().getByUserId(Long.valueOf(req.getParameter(ID_PARAMETER)));
            req.setAttribute(COMMENTS_PARAMETER, userComments);
        }

        if (gameComments.size() == 0 && userComments.size() == 0) {
            resp.sendRedirect(NO_RESULT_PAGE);
        }  else {
            req.setAttribute(URL_PARAMETER, req.getParameter(URL_PARAMETER));
            getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
        }
    }
}
