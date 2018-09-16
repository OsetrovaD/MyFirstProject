package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Subgenre;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubgenreDao {

    private static final SubgenreDao INSTANCE = new SubgenreDao();
    private static final String GET_ALL =
            "SELECT name, id  " +
            "FROM computer_games_e_shop_storage.subgenre ";
    private static final String SET_NEW_GAME_SUBGENRE =
            "INSERT INTO computer_games_e_shop_storage.game_subgenre (game_id, subgenre_id) VALUES (?, ?)";

    public List<Subgenre> getAll() {
        List<Subgenre> subgenres = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                subgenres.add(Subgenre.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return subgenres;
    }

    public boolean isSubgenreAdded(Long gameId, Integer subgenreId) {
        boolean result;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_NEW_GAME_SUBGENRE)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, subgenreId);

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public static SubgenreDao getInstance() {
        return INSTANCE;
    }
}
