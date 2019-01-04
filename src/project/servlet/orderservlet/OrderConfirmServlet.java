package project.servlet.orderservlet;

import project.dto.orderdto.OrderDto;
import project.dto.userdto.UserDataForSession;
import project.entity.Basket;
import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.PaymentForm;
import project.service.OrderService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.ConstantUtil.ORDER_CONFIRM_PAGE;
import static project.util.ConstantUtil.USERS_ORDERS_PAGE;
import static project.util.SessionParameterConstantUtil.BASKET;
import static project.util.SessionParameterConstantUtil.DELIVERY_METHOD;
import static project.util.SessionParameterConstantUtil.PAYMENT_FORM;
import static project.util.SessionParameterConstantUtil.SUM;
import static project.util.SessionParameterConstantUtil.USER_FOR_SESSION_PARAMETER;

@WebServlet("/order-confirm")
public class OrderConfirmServlet extends HttpServlet {

    private static final String PAGE_NAME = "confirm-order";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDataForSession user = (UserDataForSession) req.getSession().getAttribute(USER_FOR_SESSION_PARAMETER);
        Basket basket = (Basket) req.getSession().getAttribute(BASKET);
        DeliveryMethod delivery_method = (DeliveryMethod) req.getSession().getAttribute(DELIVERY_METHOD);
        PaymentForm payment_form = (PaymentForm) req.getSession().getAttribute(PAYMENT_FORM);
        OrderDto newOrder = OrderDto.builder()
                .userId(user.getId())
                .deliveryMethod(delivery_method)
                .paymentForm(payment_form)
                .build();

        boolean saveResult = OrderService.getInstance().save(newOrder, basket);

        if (saveResult) {
            req.getSession().removeAttribute(BASKET);
            req.getSession().removeAttribute(DELIVERY_METHOD);
            req.getSession().removeAttribute(PAYMENT_FORM);
            req.getSession().removeAttribute(SUM);
            resp.sendRedirect(USERS_ORDERS_PAGE);
        } else {
            resp.sendRedirect(ORDER_CONFIRM_PAGE);
        }
    }
}
