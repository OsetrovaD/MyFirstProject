package project.servlet;

import project.dto.userdto.NewUserDto;
import project.service.UserService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register-page")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewUserDto newUser = NewUserDto.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .firstName(req.getParameter("first_name"))
                .lastName(req.getParameter("last_name"))
                .email(req.getParameter("email"))
                .phoneNumber(req.getParameter("phone_number"))
                .address(req.getParameter("text_area"))
                .build();

        Long id = UserService.getInstance().save(newUser);
        if (id != null) {
            req.getSession().setAttribute("user_id", id);
            resp.sendRedirect("/user");
        } else {
            resp.sendRedirect("/register-page");
        }
    }
}
