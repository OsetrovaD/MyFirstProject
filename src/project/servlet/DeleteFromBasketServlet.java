package project.servlet;

import project.dto.gamedto.GameInBasket;
import project.entity.Basket;
import project.entity.enumonly.GamePlatform;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;
import static project.util.ConstantUtil.BASKET_PAGE;
import static project.util.SessionParameterConstantUtil.BASKET;

@WebServlet("/delete-from-basket")
public class DeleteFromBasketServlet extends HttpServlet {

    private static final String PLATFORM_PARAMETER = "platform";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameInBasket game = GameInBasket.builder()
                .id(Long.valueOf(req.getParameter(ID_PARAMETER)))
                .platform(GamePlatform.getByName(req.getParameter(PLATFORM_PARAMETER)))
                .build();

        Basket basket = (Basket) req.getSession().getAttribute(BASKET);
        List<GameInBasket> collect = basket.getGamesInBasket().stream().filter(t -> !t.equals(game)).collect(Collectors.toList());
        basket.setGamesInBasket(collect);

        resp.sendRedirect(BASKET_PAGE);
    }
}
