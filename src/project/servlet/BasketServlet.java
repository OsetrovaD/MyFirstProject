package project.servlet;

import project.dto.gamedto.GameInBasket;
import project.entity.Basket;
import project.util.JspPathUtil;
import project.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.ConstantUtil.BASKET;
import static project.util.ConstantUtil.NEW_ORDER;

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("url", req.getHeader("referer"));
        getServletContext().getRequestDispatcher(JspPathUtil.getPath("basket")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] numbers = req.getParameterValues("number");
        Basket basket = (Basket) req.getSession().getAttribute("basket");
        List<GameInBasket> gamesInBasket = basket.getGamesInBasket();

        if (StringUtil.areNumbersAsStringValid(numbers)) {
            for (int i = 0; i < numbers.length; i++) {
                if (StringUtil.isNotEmpty(numbers[i])) {
                    gamesInBasket.get(i).setNumber(Integer.valueOf(numbers[i]));
                }
            }
            resp.sendRedirect(NEW_ORDER);
        } else {
            resp.sendRedirect(BASKET);
        }
    }
}
