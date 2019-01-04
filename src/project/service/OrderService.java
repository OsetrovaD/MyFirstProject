package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.OrderDao;
import project.dto.orderdto.OrderDto;
import project.dto.orderdto.UserOrderForAdminDto;
import project.entity.Basket;
import project.entity.Order;
import project.entity.enumonly.Condition;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderService {

    private static final OrderService INSTANCE = new OrderService();

    public List<Order> getByUserId(Long id) {
        return OrderDao.getInstance().getByUserId(id);
    }

    public boolean save(OrderDto order, Basket basket) {
        Order newOrder = Order.builder()
                .userId(order.getUserId())
                .deliveryMethod(order.getDeliveryMethod())
                .paymentForm(order.getPaymentForm())
                .build();
        return OrderDao.getInstance().save(newOrder, basket);
    }

    public List<UserOrderForAdminDto> getByTimePeriod(LocalDate startDate, LocalDate finishDate) {
        return OrderDao.getInstance().getByTimePeriod(startDate, finishDate);
    }

    public List<UserOrderForAdminDto> getAll() {
        return OrderDao.getInstance().getAll();
    }

    public boolean updateDeliveryDate(Long orderId, LocalDate deliveryDate) {
        return OrderDao.getInstance().updateDeliveryDate(orderId, deliveryDate);
    }

    public boolean updateCondition(Long orderId, Condition condition) {
        return OrderDao.getInstance().updateCondition(orderId, condition);
    }

    public boolean updateDeliveryDateAndCondition(Long orderId, LocalDate deliveryDate, Condition condition) {
        return OrderDao.getInstance().updateDeliveryDateAndCondition(orderId, deliveryDate, condition);
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
