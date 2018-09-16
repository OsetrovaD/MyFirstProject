package project.servlet.gameservlet;

import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game-already-added")
public class GameAlreadyInBasketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("url", req.getHeader("referer"));
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("already-in-basket")).forward(req, resp);
    }
}
