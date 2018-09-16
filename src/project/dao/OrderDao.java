package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Order;
import project.entity.enumonly.Condition;
import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.GamePlatform;
import project.entity.enumonly.PaymentForm;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static project.entity.enumonly.Condition.ACCEPTED;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();
    private static final String SET_ITEMS =
            "INSERT INTO computer_games_e_shop_storage.items_in_order " +
                    "(order_id, number, game_game_platform_id) VALUES (? , ?, " +
                    "(SELECT id FROM computer_games_e_shop_storage.game_game_platform " +
                    "WHERE UPPER(game_platform) = UPPER(?) AND game_id = ?))";
    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.order_data " +
                    "(user_id, delivery_method, payment_form, condition, date, delivery_date) VALUES " +
                    "(?, ?, ?, ?, now(), null)";
    private static final String GET_BY_USER_ID =
            "SELECT id, user_id, delivery_method, payment_form, condition, date, delivery_date " +
                    "FROM computer_games_e_shop_storage.order_data " +
                    "WHERE user_id = ?";


    public List<Order> getByUserId(Long id) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .deliveryMethod(DeliveryMethod.getByName(resultSet.getString("delivery_method")))
                        .paymentForm(PaymentForm.getByName(resultSet.getString("payment_form")))
                        .condition(Condition.getByName(resultSet.getString("condition")))
                        .date(resultSet.getDate("date").toLocalDate())
                        .deliveryDate(resultSet.getDate("delivery_date").toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return orders;
    }

    private static void setItems(Long orderId, Integer itemsNumber, GamePlatform gamePlatform, Long gameId) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ITEMS)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setInt(2, itemsNumber);
            preparedStatement.setString(3, gamePlatform.getName());
            preparedStatement.setLong(4, gameId);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Long saveOrder(Order order) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setString(2, order.getDeliveryMethod().getName());
            preparedStatement.setString(3, order.getPaymentForm().getName());
            preparedStatement.setString(4, ACCEPTED.getName());

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
                order.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return id;
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
