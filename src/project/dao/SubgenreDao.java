package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Subgenre;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubgenreDao {

    private static final SubgenreDao INSTANCE = new SubgenreDao();
    private static final String GET_ALL =
            "SELECT s.name as subgenre_name, g.name as genre_name " +
            "FROM computer_games_e_shop_storage.subgenre s " +
            "JOIN computer_games_e_shop_storage.genre g on s.genre_id = g.id";

    public List<Subgenre> getAll() {
        List<Subgenre> subgenres = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                subgenres.add(Subgenre.builder()
                        .name(resultSet.getString("subgenre_name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return subgenres;
    }

    public static SubgenreDao getInstance() {
        return INSTANCE;
    }
}
