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

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;
import static project.util.ConstantUtil.SUCCESS_PAGE;
import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;

@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {

    private static final String ADD_COMMENT_ID_URL = "/add-comment?id=";
    private static final String COMMENT_PARAMETER = "comment";
    private static final String PAGE_NAME = "add-comment";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ID_PARAMETER, req.getParameter(ID_PARAMETER));
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataForSession user = (UserDataForSession) req.getSession().getAttribute(USER_FOR_SESSION_PARAMETER);
        Long addResult = CommentService.getInstance().add(NewCommentDto.builder()
                .gameId(Long.valueOf(req.getParameter(ID_PARAMETER)))
                .userId(user.getId())
                .text(req.getParameter(COMMENT_PARAMETER))
                .build());

        if (addResult > 0) {
            resp.sendRedirect(SUCCESS_PAGE);
        } else {
            resp.sendRedirect(ADD_COMMENT_ID_URL + req.getParameter(ID_PARAMETER));
        }
    }
}
