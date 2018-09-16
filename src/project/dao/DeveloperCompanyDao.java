package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.DeveloperCompany;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeveloperCompanyDao {

    private static final DeveloperCompanyDao INSTANCE = new DeveloperCompanyDao();
    private static final String GET_ALL = "SELECT id, name FROM computer_games_e_shop_storage.game_developer_company";
    private static final String ADD_NEW_DEVELOPER_COMPANY = "INSERT INTO computer_games_e_shop_storage.game_developer_company (name) VALUES (?)";
    private static final String UPDATE_DEVELOPER_COMPANY =
            "UPDATE computer_games_e_shop_storage.game " +
                    "SET company_id = ? " +
                    "WHERE id = ?";

    public boolean updateDeveloperCompany(Integer companyId, Long gameId) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DEVELOPER_COMPANY)) {
            preparedStatement.setInt(1, companyId);
            preparedStatement.setLong(2, gameId);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public Integer addNewDeveloperCompany(String companyName) {
        Integer id = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_DEVELOPER_COMPANY, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, companyName);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return id;
    }

    public List<DeveloperCompany> getAll() {
        List<DeveloperCompany> companies = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                companies.add(DeveloperCompany.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return companies;
    }

    public static DeveloperCompanyDao getInstance() {
        return INSTANCE;
    }
}
