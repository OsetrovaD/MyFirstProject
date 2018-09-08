package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageDao {
    private static final StorageDao INSTANCE = new StorageDao();
    private static final String GET_BY_GAME_ID =
            "SELECT id, game_id, number, last_addition_date " +
                    "FROM computer_games_e_shop_storage.storage " +
                    "WHERE game_id = ?";
    private static final String GET_BY_TIME_PERIOD =
            "SELECT id, game_id, number, last_addition_date " +
                    "FROM computer_games_e_shop_storage.storage " +
                    "WHERE last_addition_date BETWEEN ? AND ?";
    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.storage (game_id, number, last_addition_date) " +
                    "VALUES (?, ?, now())";
    private static final String UPDATE =
            "UPDATE computer_games_e_shop_storage.storage " +
                    "SET number = ?, last_addition_date = now() " +
                    "WHERE game_id = ?";

    public Long save(Storage storage) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, storage.getGameId());
            preparedStatement.setShort(2, storage.getNumber());

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
                storage.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static boolean update(Storage storage) {
        boolean result = false;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setShort(1, storage.getNumber());
            preparedStatement.setLong(2, storage.getGameId());

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Storage getByGameId(Long id) {
        Storage storage = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_GAME_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                storage = Storage.builder()
                        .id(resultSet.getLong("id"))
                        .gameId(resultSet.getLong("game_id"))
                        .number(resultSet.getShort("number"))
                        .lastAdditionDate(resultSet.getDate("last_addition_date"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storage;
    }

    public static List<Storage> getByTimePeriod(String startDate, String finishDate) {
        List<Storage> storages = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_TIME_PERIOD)) {
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(finishDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                storages.add(Storage.builder()
                        .id(resultSet.getLong("id"))
                        .gameId(resultSet.getLong("game_id"))
                        .number(resultSet.getShort("number"))
                        .lastAdditionDate(resultSet.getDate("last_addition_date"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storages;
    }

    public static StorageDao getInstance() {
        return INSTANCE;
    }
}
