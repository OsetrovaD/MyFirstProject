package project.servlet.searchresult;

import project.dto.orderdto.UserOrderForAdminDto;
import project.service.OrderService;
import project.util.JspPathUtil;
import project.util.LocalDateFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static project.util.MappingForAdminConst.CHOOSE_ORDERS_BY_PERIOD;

@WebServlet("/order-search-result")
public class OrderSearchResultServlet extends HttpServlet {

    private static final String PAGE_NAME = "order-search-result";
    private static final String START_DATE_PARAMETER = "start_date";
    private static final String FINISH_DATE_PARAMETER = "finish_date";
    private static final String ORDERS_LIST_PARAMETER = "ordersList";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate startDate = LocalDateFormatter.format(req.getParameter(START_DATE_PARAMETER));
        LocalDate finishDate = LocalDateFormatter.format(req.getParameter(FINISH_DATE_PARAMETER));

        if (startDate.compareTo(finishDate) <= 0) {
            List<UserOrderForAdminDto> orders = OrderService.getInstance().getByTimePeriod(startDate, finishDate);
            req.setAttribute(ORDERS_LIST_PARAMETER, orders);
            getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
        } else {
            resp.sendRedirect(CHOOSE_ORDERS_BY_PERIOD);
        }
    }
}
