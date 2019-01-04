package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.ItemInOrderDto;
import project.dto.gamedto.GameInBasket;
import project.dto.orderdto.UserOrderForAdminDto;
import project.dto.storagedto.ChangeStorageNumberDto;
import project.dto.userdto.UserLoginIdDto;
import project.entity.Basket;
import project.entity.Order;
import project.entity.enumonly.Condition;
import project.entity.enumonly.DeliveryMethod;
import project.entity.enumonly.GamePlatform;
import project.entity.enumonly.PaymentForm;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static project.util.StorageUpdateConstantUtil.GET_FROM_STORAGE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();
    private static final String GET_ALL =
            "SELECT o.id, o.delivery_method, o.payment_form, o.condition, o.date, o.delivery_date, o.user_id, u.login " +
                    "FROM computer_games_e_shop_storage.order_data o " +
                    "JOIN computer_games_e_shop_storage.user_data u on o.user_id = u.id " +
                    "ORDER BY o.date DESC";
    private static final String SET_ITEMS =
            "INSERT INTO computer_games_e_shop_storage.items_in_order " +
                    "(order_id, number, game_game_platform_id) VALUES (? , ?, " +
                    "(SELECT id FROM computer_games_e_shop_storage.game_game_platform " +
                    "WHERE UPPER(game_platform) = UPPER(?) AND game_id = ?))";
    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.order_data " +
                    "(user_id, delivery_method, payment_form, condition, date, delivery_date) VALUES " +
                    "(?, ?, ?, 'Принят', now(), null)";
    private static final String GET_BY_USER_ID =
            "SELECT o.id, o.delivery_method, o.payment_form, o.condition, o.date, o.delivery_date, i.number, g.game_platform, g.price, g2.name, g2.id as game_id " +
                    "FROM computer_games_e_shop_storage.order_data o " +
                    "JOIN computer_games_e_shop_storage.items_in_order i on o.id = i.order_id " +
                    "JOIN computer_games_e_shop_storage.game_game_platform g on i.game_game_platform_id = g.id " +
                    "JOIN computer_games_e_shop_storage.game g2 on g.game_id = g2.id " +
                    "WHERE user_id = ? " +
                    "ORDER BY o.id";
    private static final String GET_BY_TIME_PERIOD =
            "SELECT o.id, o.user_id, o.condition, o.payment_form, o.delivery_method, o.date, o.delivery_date, u.login " +
                    "FROM computer_games_e_shop_storage.order_data o " +
                    "JOIN computer_games_e_shop_storage.user_data u on o.user_id = u.id " +
                    "WHERE date BETWEEN ? AND ?";
    private static final String UPDATE_DELIVERY_DATE_AND_CONDITION =
            "UPDATE computer_games_e_shop_storage.order_data " +
                    "SET condition = ?, delivery_date = ? " +
                    "WHERE id = ?";
    private static final String UPDATE_DELIVERY_DATE =
            "UPDATE computer_games_e_shop_storage.order_data " +
                    "SET delivery_date = ? " +
                    "WHERE id = ?";
    private static final String UPDATE_CONDITION =
            "UPDATE computer_games_e_shop_storage.order_data " +
                    "SET condition = ? " +
                    "WHERE id = ?";

    public List<UserOrderForAdminDto> getAll() {
        List<UserOrderForAdminDto> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while (resultSet.next()) {
                users.add(UserOrderForAdminDto.builder()
                        .id(resultSet.getLong("id"))
                        .condition(Condition.getByName(resultSet.getString("condition")))
                        .deliveryMethod(DeliveryMethod.getByName(resultSet.getString("delivery_method")))
                        .paymentForm(PaymentForm.getByName(resultSet.getString("payment_form")))
                        .date(resultSet.getDate("date").toLocalDate())
                        .deliveryDate(resultSet.getDate("delivery_date") != null ? resultSet.getDate("delivery_date").toLocalDate() : null)
                        .user(UserLoginIdDto.builder()
                                .id(resultSet.getLong("user_id"))
                                .login(resultSet.getString("login"))
                                .build())
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return users;
    }

    public boolean updateCondition(Long orderId, Condition condition) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONDITION)) {
            preparedStatement.setLong(2, orderId);
            preparedStatement.setString(1, condition.getName());

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public boolean updateDeliveryDate(Long orderId, LocalDate deliveryDate) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DELIVERY_DATE)) {
            preparedStatement.setDate(1, Date.valueOf(deliveryDate));
            preparedStatement.setLong(2, orderId);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public boolean updateDeliveryDateAndCondition(Long orderId, LocalDate deliveryDate, Condition condition) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DELIVERY_DATE_AND_CONDITION)) {
            preparedStatement.setString(1, condition.getName());
            preparedStatement.setDate(2, Date.valueOf(deliveryDate));
            preparedStatement.setLong(3, orderId);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public List<UserOrderForAdminDto> getByTimePeriod(LocalDate startDate, LocalDate finishDate) {
        List<UserOrderForAdminDto> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_TIME_PERIOD)) {
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(finishDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orders.add(UserOrderForAdminDto.builder()
                        .id(resultSet.getLong("id"))
                        .condition(Condition.getByName(resultSet.getString("condition")))
                        .deliveryMethod(DeliveryMethod.getByName(resultSet.getString("delivery_method")))
                        .paymentForm(PaymentForm.getByName(resultSet.getString("payment_form")))
                        .date(resultSet.getDate("date").toLocalDate())
                        .deliveryDate(resultSet.getDate("delivery_date") != null ? resultSet.getDate("delivery_date").toLocalDate() : null)
                        .user(UserLoginIdDto.builder()
                                .id(resultSet.getLong("user_id"))
                                .login(resultSet.getString("login"))
                                .build())
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return orders;
    }

    public boolean save(Order order, Basket basket) {
        boolean setItemsResult = false;

        Long newOrderId = create(order);

        if (newOrderId != null) {
            List<GameInBasket> gamesInBasket = basket.getGamesInBasket();
            for (GameInBasket gameInBasket : gamesInBasket) {
                boolean setItem = setItems(newOrderId, gameInBasket);

                ChangeStorageNumberDto tempStorage = ChangeStorageNumberDto.builder()
                        .gameId(gameInBasket.getId())
                        .platform(gameInBasket.getPlatform())
                        .build();

                boolean updateStorage = StorageDao.getInstance().update(tempStorage, gameInBasket.getNumber(), GET_FROM_STORAGE);
                setItemsResult = setItem && updateStorage;
            }
        }

        return setItemsResult;
    }

    public List<Order> getByUserId(Long userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Order order = null;
            while (resultSet.next()) {
                if (order == null || order.getId() != resultSet.getLong("id")) {
                    orders.add(order = Order.builder()
                            .id(resultSet.getLong("id"))
                            .userId(userId)
                            .deliveryMethod(DeliveryMethod.getByName(resultSet.getString("delivery_method")))
                            .paymentForm(PaymentForm.getByName(resultSet.getString("payment_form")))
                            .condition(Condition.getByName(resultSet.getString("condition")))
                            .date(resultSet.getDate("date").toLocalDate())
                            .deliveryDate(resultSet.getDate("delivery_date") != null ? resultSet.getDate("delivery_date").toLocalDate() : null)
                            .items(new ArrayList<>())
                            .build());
                }
                order.getItems().add(ItemInOrderDto.builder()
                        .gameId(resultSet.getLong("game_id"))
                        .gameName(resultSet.getString("name"))
                        .number(resultSet.getInt("number"))
                        .platform(GamePlatform.getByName(resultSet.getString("game_platform")))
                        .price(resultSet.getInt("price"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return orders;
    }

    private boolean setItems(Long orderId, GameInBasket gameInBasket) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_ITEMS)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setInt(2, gameInBasket.getNumber());
            preparedStatement.setString(3, gameInBasket.getPlatform().getName());
            preparedStatement.setLong(4, gameInBasket.getId());

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    private Long create(Order order) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setString(2, order.getDeliveryMethod().getName());
            preparedStatement.setString(3, order.getPaymentForm().getName());

            preparedStatement.executeUpdate();
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
