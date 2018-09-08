package project.servlet;

import project.entity.Comment;
import project.service.CommentService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        List<Comment> comments = CommentService.getInstance().getByUserOrGameId(userId);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("comment-list")).forward(req, resp);
    }
}
