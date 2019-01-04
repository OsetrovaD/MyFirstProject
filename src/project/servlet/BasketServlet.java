package project.servlet;

import project.dto.gamedto.GameInBasket;
import project.dto.storagedto.CheckStorageDto;
import project.entity.Basket;
import project.service.StorageService;
import project.util.JspPathUtil;
import project.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static project.util.CommonRequestParameterConstantUtil.NUMBER_PARAMETER;
import static project.util.ConstantUtil.BASKET_PAGE;
import static project.util.ConstantUtil.NEW_ORDER_PAGE;
import static project.util.SessionParameterConstantUtil.BASKET;
import static project.util.SessionParameterConstantUtil.SUM;

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {

    private static final String PAGE_NAME = "basket";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] numbers = req.getParameterValues(NUMBER_PARAMETER);
        Basket basket = (Basket) req.getSession().getAttribute(BASKET);
        List<GameInBasket> gamesInBasket = basket.getGamesInBasket();

        if (StringUtil.areNumbersAsStringValid(numbers) && isEnoughInStorage(numbers, req)) {
            for (int i = 0; i < numbers.length; i++) {
                if (StringUtil.isNotEmpty(numbers[i])) {
                    gamesInBasket.get(i).setNumber(Short.valueOf(numbers[i]));
                }
            }

            req.getSession().setAttribute(SUM, sum(gamesInBasket));
            resp.sendRedirect(NEW_ORDER_PAGE);
        } else {
            resp.sendRedirect(BASKET_PAGE);
        }
    }

    private Integer sum(List<GameInBasket> gamesInBasket) {
        Integer sum = 0;
        for (GameInBasket gameInBasket : gamesInBasket) {
            sum += gameInBasket.getNumber()*gameInBasket.getPrice();
        }

        return sum;
    }

    private boolean isEnoughInStorage(String[] numbers, HttpServletRequest req) {
        boolean result = true;
        Basket basket = (Basket) req.getSession().getAttribute("basket");
        List<GameInBasket> gamesInBasket = basket.getGamesInBasket();

        List<CheckStorageDto> numberFromStorage = getNumberFromStorageFor(gamesInBasket);

        for (int i = 0; i < numbers.length; i++) {
            if (StringUtil.isNotEmpty(numbers[i])) {
                if (Short.valueOf(numbers[i]) > numberFromStorage.get(i).getNumber()) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private List<CheckStorageDto> getNumberFromStorageFor(List<GameInBasket> gamesInBasket) {
        List<CheckStorageDto> numbers = new ArrayList<>();

        for (GameInBasket gameInBasket : gamesInBasket) {
            numbers.add(StorageService.getInstance()
                    .getByGameIdAndPlatform(gameInBasket.getPlatform(), gameInBasket.getId()));
        }

        return numbers;
    }
}
