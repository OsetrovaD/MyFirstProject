package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.userdto.UserDataForSession;
import project.dto.userdto.UserDto;
import project.dto.userdto.UserForAdminDto;
import project.entity.User;
import project.entity.enumonly.Role;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static project.entity.enumonly.Role.USER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static final String GET_ALL =
            "SELECT id as user_id, login, first_name, " +
                    "last_name, phone_number, e_mail, address, role " +
            "FROM computer_games_e_shop_storage.user_data";
    private static final String UPDATE_CONTACTS =
            "UPDATE computer_games_e_shop_storage.user_data " +
            "SET first_name=?, last_name=?, phone_number = ?, e_mail = ?, address = ? " +
            "WHERE id = ?";
    private static final String UPDATE_ROLE = "UPDATE computer_games_e_shop_storage.user_data " +
            "SET role = ? " +
            "WHERE id = ?";
    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.user_data " +
            "(login, password, first_name, last_name, phone_number, e_mail, address, role) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_LOGIN_AND_PASSWORD = "SELECT id, login, role FROM computer_games_e_shop_storage.user_data WHERE login = ? AND password = ?";

    public UserDataForSession getByLoginAndPassword(UserDto userCheck) {
        UserDataForSession userData = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, userCheck.getLogin());
            preparedStatement.setString(2, userCheck.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userData = UserDataForSession.builder()
                        .id(resultSet.getLong("id"))
                        .login(resultSet.getString("login"))
                        .role(Role.getByName(resultSet.getString("role")))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return userData;
    }

    public Long save(User user) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, USER.getName());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
                user.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return id;
    }

    public boolean updateRole(Long userId, Role role) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.setLong(2, userId);

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public boolean update(User user) {
        boolean result;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACTS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setLong(6, user.getId());

            result = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    public List<UserForAdminDto> getAll() {
        List<UserForAdminDto> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                users.add(UserForAdminDto.builder()
                        .id(resultSet.getLong("user_id"))
                        .login(resultSet.getString("login"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("e_mail"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .role(Role.getByName(resultSet.getString("role")))
                        .address(resultSet.getString("address"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return users;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
