package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Genre;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreDao {

    private static final GenreDao INSTANCE = new GenreDao();
    private static final String GET_ALL = "SELECT name FROM computer_games_e_shop_storage.genre";

    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                genres.add(Genre.builder()
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return genres;
    }

    public static GenreDao getInstance() {
        return INSTANCE;
    }
}
