package project.servlet;

import project.dto.commentdto.NewCommentDto;
import project.dto.userdto.UserDataForSession;
import project.service.CommentService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("id", req.getParameter("id"));
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("add-comment")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataForSession user = (UserDataForSession) req.getSession().getAttribute("user");
        Long addResult = CommentService.getInstance().add(NewCommentDto.builder()
                .gameId(Long.valueOf(req.getParameter("id")))
                .userId(user.getId())
                .text(req.getParameter("comment"))
                .build());

        if (addResult > 0) {
            resp.sendRedirect("/success");
        } else {
            resp.sendRedirect("/add-comment?id=" + req.getParameter("id"));
        }
    }
}
