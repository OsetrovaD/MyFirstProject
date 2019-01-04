package project.servlet.orderservlet;

import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.PaymentForm;
import project.service.DeliveryMethodService;
import project.service.PaymentFormService;
import project.util.JspPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.util.ConstantUtil.NEW_ORDER_PAGE;
import static project.util.ConstantUtil.ORDER_CONFIRM_PAGE;
import static project.util.SessionParameterConstantUtil.DELIVERY_METHOD;
import static project.util.SessionParameterConstantUtil.PAYMENT_FORM;

@WebServlet("/new-order")
public class NewOrderServlet extends HttpServlet {

    private static final String PAGE_NAME = "new-order";
    private static final String PAYMENT_FORM_PARAMETER = "paymentForm";
    private static final String DELIVERY_METHOD_PARAMETER = "deliveryMethod";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute(PAYMENT_FORM_PARAMETER, PaymentFormService.getInstance().getAll());
        req.setAttribute(DELIVERY_METHOD_PARAMETER, DeliveryMethodService.getInstance().getAll());
        getServletContext().getRequestDispatcher(JspPathUtil.getPath(PAGE_NAME)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (areAllParameterValid(req)) {
            req.getSession().setAttribute(PAYMENT_FORM, PaymentForm.valueOf(req.getParameter(PAYMENT_FORM)));
            req.getSession().setAttribute(DELIVERY_METHOD, DeliveryMethod.valueOf(req.getParameter(DELIVERY_METHOD)));
            resp.sendRedirect(ORDER_CONFIRM_PAGE);
        } else {
            resp.sendRedirect(NEW_ORDER_PAGE);
        }

    }

    private boolean areAllParameterValid(HttpServletRequest req) {
        return req.getParameter(PAYMENT_FORM) != null && req.getParameter(DELIVERY_METHOD) != null;
    }
}
