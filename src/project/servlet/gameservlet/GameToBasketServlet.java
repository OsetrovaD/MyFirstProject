package project.servlet.gameservlet;

import project.dto.gamedto.GameInBasket;
import project.entity.Basket;
import project.entity.enumonly.GamePlatform;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.ConstantUtil.BASKET;
import static project.util.ConstantUtil.GAME_ALREADY_IN_BASKET;

@WebServlet("/add-to-basket")
public class GameToBasketServlet extends HttpServlet {

    private static final int DEFAULT_NUMBER = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String platform = req.getParameter("platform");
        String price = req.getParameter("price");

        GameInBasket currentGame = GameInBasket.builder()
                .id(Long.valueOf(id))
                .name(name)
                .platform(GamePlatform.valueOf(platform))
                .price(Integer.valueOf(price))
                .number(DEFAULT_NUMBER)
                .build();

        if (req.getSession().getAttribute("basket") == null) {
            Basket basket = new Basket();
            basket.getGamesInBasket().add(currentGame);
            req.getSession().setAttribute("basket", basket);
            resp.sendRedirect(BASKET);
        } else if (isGameAlreadyAdded(req, currentGame)) {
            resp.sendRedirect(GAME_ALREADY_IN_BASKET);
        } else {
            Basket basket = (Basket) req.getSession().getAttribute("basket");
            basket.getGamesInBasket().add(currentGame);
            resp.sendRedirect(BASKET);
        }

    }

    private boolean isGameAlreadyAdded(HttpServletRequest req, GameInBasket game) {
        Basket basket = (Basket) req.getSession().getAttribute("basket");
        return basket.getGamesInBasket().stream().anyMatch(n -> n.equals(game));
    }
}

