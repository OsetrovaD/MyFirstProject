package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.storagedto.ChangeStorageNumberDto;
import project.dto.storagedto.StorageDto;
import project.entity.Storage;
import project.entity.enumonly.GamePlatform;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static project.util.StorageUpdateConstantUtil.ADD_TO_STORAGE;
import static project.util.StorageUpdateConstantUtil.GET_FROM_STORAGE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageDao {
    private static final StorageDao INSTANCE = new StorageDao();
    private static final String GET_BY_GAME_ID =
            "SELECT s.number, s.last_addition_date, g.game_platform " +
                    "FROM computer_games_e_shop_storage.storage s " +
                    "LEFT JOIN computer_games_e_shop_storage.game_game_platform g on s.game_game_platform_id = g.id " +
                    "WHERE g.game_id = ?";
    private static final String GET_BY_GAME_ID_AND_PLATFORM =
            "SELECT s.number, g.game_platform " +
                    "FROM computer_games_e_shop_storage.storage s " +
                    "LEFT JOIN computer_games_e_shop_storage.game_game_platform g on s.game_game_platform_id = g.id " +
                    "WHERE g.game_id = ? AND UPPER(g.game_platform) = UPPER(?)";
    private static final String GET_BY_TIME_PERIOD =
            "SELECT ggp.game_platform, s.number, s.last_addition_date, g.name " +
                    "FROM computer_games_e_shop_storage.storage s " +
                    "LEFT JOIN computer_games_e_shop_storage.game_game_platform ggp on s.game_game_platform_id = ggp.id " +
                    "LEFT JOIN computer_games_e_shop_storage.game g on ggp.game_id = g.id " +
                    "WHERE last_addition_date BETWEEN ? AND ?";
    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.storage (game_game_platform_id, number, last_addition_date) " +
                    "VALUES (?, ?, now())";
    private static final String UPDATE =
            "UPDATE computer_games_e_shop_storage.storage " +
                    "SET number = ? + number, last_addition_date = now() " +
                    "WHERE game_game_platform_id = " +
                    "(SELECT id FROM computer_games_e_shop_storage.game_game_platform WHERE UPPER(game_platform) = UPPER(?) AND game_id = ?)";
    private static final String UPDATE_AFTER_ORDER =
            "UPDATE computer_games_e_shop_storage.storage " +
                    "SET number = number - ?, last_addition_date = now() " +
                    "WHERE game_game_platform_id = " +
                    "(SELECT id FROM computer_games_e_shop_storage.game_game_platform WHERE UPPER(game_platform) = UPPER(?) AND game_id = ?)";
    private static final Map<String, String> UPDATE_QUERY = new HashMap<>();

    static {
        UPDATE_QUERY.put(ADD_TO_STORAGE, UPDATE);
        UPDATE_QUERY.put(GET_FROM_STORAGE, UPDATE_AFTER_ORDER);
    }

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

    public Storage getByGameIdAndPlatform(GamePlatform platform, Long gameId) {
        Storage storage = new Storage();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_GAME_ID_AND_PLATFORM)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, platform.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                storage.setGameId(gameId);
                storage.setPlatform(GamePlatform.getByName(resultSet.getString("game_platform")));
                storage.setNumber(resultSet.getShort("number"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return storage;
    }

    public boolean update(ChangeStorageNumberDto gameData, Short number, String query) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY.get(query))) {
            preparedStatement.setShort(1, number);
            preparedStatement.setString(2, gameData.getPlatform().getName());
            preparedStatement.setLong(3, gameData.getGameId());

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public List<Storage> getByGameId(Long id) {
        List<Storage> storage = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_GAME_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                storage.add(Storage.builder()
                        .gameId(id)
                        .platform(GamePlatform.getByName(resultSet.getString("game_platform")))
                        .number(resultSet.getShort("number"))
                        .lastAdditionDate(resultSet.getDate("last_addition_date").toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return storage;
    }

    public List<StorageDto> getByTimePeriod(String startDate, String finishDate) {
        List<StorageDto> storages = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_TIME_PERIOD)) {
            preparedStatement.setDate(1, Date.valueOf(startDate));
            preparedStatement.setDate(2, Date.valueOf(finishDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                storages.add(StorageDto.builder()
                        .gameName(resultSet.getString("name"))
                        .platform(GamePlatform.getByName(resultSet.getString("game_platform")))
                        .number(resultSet.getShort("number"))
                        .lastAdditionDate(resultSet.getDate("last_addition_date").toLocalDate())
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
