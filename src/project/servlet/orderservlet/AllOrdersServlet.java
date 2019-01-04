package project.servlet.orderservlet;

import project.dto.orderdto.UserOrderForAdminDto;
import project.service.OrderService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/all-orders")
public class AllOrdersServlet extends HttpServlet {

    private static final String ALL_ORDERS_PARAMETER = "allOrders";
    private static final String PAGE_NAME = "all-orders";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserOrderForAdminDto> orders = OrderService.getInstance().getAll();
        req.setAttribute(ALL_ORDERS_PARAMETER, orders);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
