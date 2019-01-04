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

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;
import static project.util.CommonRequestParameterConstantUtil.NAME_PARAMETER;
import static project.util.ConstantUtil.BASKET_PAGE;
import static project.util.ConstantUtil.GAME_ALREADY_IN_BASKET;
import static project.util.SessionParameterConstantUtil.BASKET;

@WebServlet("/add-to-basket")
public class GameToBasketServlet extends HttpServlet {

    private static final short DEFAULT_NUMBER = 1;
    private static final String PLATFORM_PARAMETER = "platform";
    private static final String PRICE_PARAMETER = "price";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_PARAMETER);
        String name = req.getParameter(NAME_PARAMETER);
        String platform = req.getParameter(PLATFORM_PARAMETER);
        String price = req.getParameter(PRICE_PARAMETER);

        GameInBasket currentGame = GameInBasket.builder()
                .id(Long.valueOf(id))
                .name(name)
                .platform(GamePlatform.valueOf(platform))
                .price(Integer.valueOf(price))
                .number(DEFAULT_NUMBER)
                .build();

        if (req.getSession().getAttribute(BASKET) == null) {
            Basket basket = new Basket();
            basket.getGamesInBasket().add(currentGame);
            req.getSession().setAttribute(BASKET, basket);
            resp.sendRedirect(BASKET_PAGE);
        } else if (isGameAlreadyAdded(req, currentGame)) {
            resp.sendRedirect(GAME_ALREADY_IN_BASKET);
        } else {
            Basket basket = (Basket) req.getSession().getAttribute(BASKET);
            basket.getGamesInBasket().add(currentGame);
            resp.sendRedirect(BASKET_PAGE);
        }

    }

    private boolean isGameAlreadyAdded(HttpServletRequest req, GameInBasket game) {
        Basket basket = (Basket) req.getSession().getAttribute(BASKET);
        return basket.getGamesInBasket().stream().anyMatch(n -> n.equals(game));
    }
}

