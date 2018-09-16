package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Storage;
import project.exception.DaoException;

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
            "SELECT id, game_game_platform_id, number, last_addition_date " +
                    "FROM computer_games_e_shop_storage.storage " +
                    "WHERE game_game_platform_id = ?";
    private static final String GET_BY_TIME_PERIOD =
            "SELECT id, game_game_platform_id, number, last_addition_date " +
                    "FROM computer_games_e_shop_storage.storage " +
                    "WHERE last_addition_date BETWEEN ? AND ?";
    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.storage (game_game_platform_id, number, last_addition_date) " +
                    "VALUES (?, ?, now())";
    private static final String UPDATE =
            "UPDATE computer_games_e_shop_storage.storage " +
                    "SET number = ?, last_addition_date = now() " +
                    "WHERE game_game_platform_id = ?";

    public Long save(Long platformPriceId, Short number) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, platformPriceId);
            preparedStatement.setShort(2, number);

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return id;
    }

    public boolean update(Storage storage) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setShort(1, storage.getNumber());
            preparedStatement.setLong(2, storage.getGameId());

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public Storage getByGameId(Long id) {
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
            throw new DaoException(e);
        }

        return storage;
    }

    public List<Storage> getByTimePeriod(String startDate, String finishDate) {
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
            throw new DaoException(e);
        }

        return storages;
    }

    public static StorageDao getInstance() {
        return INSTANCE;
    }
}
