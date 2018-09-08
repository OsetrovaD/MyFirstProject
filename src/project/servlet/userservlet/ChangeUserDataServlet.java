package project.servlet.userservlet;

import project.dto.userdto.ChangeUserDataDto;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/change-data")
public class ChangeUserDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("change-data")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("user_id");
        ChangeUserDataDto changes = ChangeUserDataDto.builder()
                .firstName(req.getParameter("first_name"))
                .lastName(req.getParameter("last_name"))
                .email(req.getParameter("email"))
                .phoneNumber(req.getParameter("phone_number"))
                .address(req.getParameter("text_area"))
                .build();
        boolean result = UserService.getInstance().update(changes, userId);

        if (result) {
            resp.sendRedirect("/success");
        } else {
            resp.sendRedirect("/change-data");
        }
    }
}
