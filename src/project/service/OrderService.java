package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.OrderDao;
import project.entity.Order;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderService {

    private static final OrderService INSTANCE = new OrderService();

    public List<Order> getByUserId(Long id) {
        return OrderDao.getInstance().getByUserId(id);
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
