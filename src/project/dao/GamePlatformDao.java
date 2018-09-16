package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.enumonly.GamePlatform;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GamePlatformDao {

    private static final GamePlatformDao INSTANCE = new GamePlatformDao();
    private static final String SET_GAME_PLATFORMS =
            "INSERT INTO computer_games_e_shop_storage.game_game_platform (game_id, price, game_platform) VALUES (?, ?, ?)";

    public Long setGamePlatform(Long gameId, Integer price, GamePlatform platform) {
        Long id = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_GAME_PLATFORMS, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, price);
            preparedStatement.setString(3, platform.getName());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return id;
    }

    public static GamePlatformDao getInstance() {
        return INSTANCE;
    }
}
