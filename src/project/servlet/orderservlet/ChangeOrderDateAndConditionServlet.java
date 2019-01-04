package project.servlet.orderservlet;

import project.entity.enumonly.Condition;
import project.service.ConditionService;
import project.service.OrderService;
import project.util.JspPathUtil;
import project.util.LocalDateFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.CommonRequestParameterConstantUtil.ID_PARAMETER;
import static project.util.ConstantUtil.SUCCESS_PAGE;
import static project.util.MappingForAdminConst.CHANGE_ORDER_DATA;
import static project.util.SessionParameterConstantUtil.CHANGED_ORDER_ID;

@WebServlet("/change-order-data")
public class ChangeOrderDateAndConditionServlet extends HttpServlet {

    private static final String DEFAULT_VALUE = "default";
    private static final String CONDITIONS_PARAMETER = "conditions";
    private static final String PAGE_NAME = "change-order-data";
    private static final String ORDER_CONDITION_PARAMETER = "order_condition";
    private static final String DELIVERY_DATE_PARAMETER = "delivery_date";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(CHANGED_ORDER_ID, req.getParameter(ID_PARAMETER));
        req.setAttribute(CONDITIONS_PARAMETER, ConditionService.getInstance().getAll());
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = (String) req.getSession().getAttribute(CHANGED_ORDER_ID);
        Long id = Long.valueOf(orderId);
        String orderCondition = req.getParameter(ORDER_CONDITION_PARAMETER);
        String deliveryDate = req.getParameter(DELIVERY_DATE_PARAMETER);

        if (isNothingChosen(req)) {
            resp.sendRedirect(CHANGE_ORDER_DATA);
        } else {
            boolean result;
            if (isEverythingChosen(req)) {
                result = OrderService.getInstance()
                        .updateDeliveryDateAndCondition(id, LocalDateFormatter.format(deliveryDate), Condition.valueOf(orderCondition));
            } else if (DEFAULT_VALUE.equals(orderCondition)) {
                result = OrderService.getInstance().updateDeliveryDate(id, LocalDateFormatter.format(deliveryDate));
            } else {
                result = OrderService.getInstance().updateCondition(id, Condition.valueOf(orderCondition));
            }

            if (result) {
                req.getSession().removeAttribute(CHANGED_ORDER_ID);
                resp.sendRedirect(SUCCESS_PAGE);
            } else {
                resp.sendRedirect(CHANGE_ORDER_DATA);
            }
        }
    }

    private boolean isEverythingChosen(HttpServletRequest req) {
        String orderCondition = req.getParameter(ORDER_CONDITION_PARAMETER);
        String deliveryDate = req.getParameter(DELIVERY_DATE_PARAMETER);

        return !DEFAULT_VALUE.equals(orderCondition) && !deliveryDate.isEmpty();
    }

    private boolean isNothingChosen(HttpServletRequest req) {
        String orderCondition = req.getParameter(ORDER_CONDITION_PARAMETER);
        String deliveryDate = req.getParameter(DELIVERY_DATE_PARAMETER);

        return DEFAULT_VALUE.equals(orderCondition) && deliveryDate.isEmpty();
    }
}
