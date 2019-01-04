package project.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/change-locale")
public class ChangeLocaleServlet extends HttpServlet {

    private static final String LANGUAGE_PARAMETER = "language";
    private static final String HEADER_PARAMETER = "Referer";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(LANGUAGE_PARAMETER, req.getParameter(LANGUAGE_PARAMETER));
        resp.sendRedirect(req.getHeader(HEADER_PARAMETER));
    }
}
