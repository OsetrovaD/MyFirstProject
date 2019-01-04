package project.servlet.gameservlet;

import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.CommonRequestParameterConstantUtil.URL_PARAMETER;

@WebServlet("/game-already-added")
public class GameAlreadyInBasketServlet extends HttpServlet {

    private static final String PAGE_NAME = "already-in-basket";
    private static final String HEADER_PARAMETER = "referer";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(URL_PARAMETER, req.getHeader(HEADER_PARAMETER));
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
