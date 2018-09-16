package project.servlet;

import project.dto.commentdto.GameCommentDto;
import project.dto.commentdto.UserCommentDto;
import project.entity.Comment;
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

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameCommentDto> gameComments = new ArrayList<>();
        List<UserCommentDto> userComments = new ArrayList<>();

        if (req.getParameter("url").equals("game-info")) {
            gameComments = CommentService.getInstance().getByGameId(Long.valueOf(req.getParameter("id")));
            req.setAttribute("comments", gameComments);
        } else {
            userComments = CommentService.getInstance().getByUserId(Long.valueOf(req.getParameter("id")));
            req.setAttribute("comments", userComments);
        }

        if (gameComments.size() == 0 && userComments.size() == 0) {
            resp.sendRedirect("/no-result");
        }  else {
            req.setAttribute("url", req.getParameter("url"));
            getServletContext().getRequestDispatcher(JspPathUtil.getPath("comment-list")).forward(req, resp);
        }
    }
}
