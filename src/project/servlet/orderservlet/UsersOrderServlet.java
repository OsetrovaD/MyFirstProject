package project.servlet.orderservlet;

import project.dto.userdto.UserDataForSession;
import project.entity.Order;
import project.service.OrderService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;

@WebServlet("/orders")
public class UsersOrderServlet extends HttpServlet {

    private static final String PAGE_NAME = "orders-data";
    private static final String ORDERS_PARAMETER = "orders";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataForSession user = (UserDataForSession) req.getSession().getAttribute(USER_FOR_SESSION_PARAMETER);
        List<Order> orders = OrderService.getInstance().getByUserId(user.getId());
        req.setAttribute(ORDERS_PARAMETER, orders);
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }
}
