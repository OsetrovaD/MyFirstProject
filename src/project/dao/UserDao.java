package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.userdto.UserDataForSession;
import project.dto.userdto.UserDto;
import project.entity.Comment;
import project.entity.User;
import project.entity.enumonly.Role;
import project.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static project.entity.enumonly.Role.USER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static final String GET_BY_ID =
            "SELECT ud.id as user_id, ud.login, ud.password, ud.first_name, " +
                    "ud.last_name, ud.phone_number, ud.e_mail, ud.address, ud.role," +
                    "c2.id as comment_id, c2.game_id, c2.text, c2.date " +
            "FROM computer_games_e_shop_storage.user_data ud " +
            "LEFT JOIN computer_games_e_shop_storage.comment c2 ON ud.id = c2.user_id " +
            "WHERE ud.id = ?";
    private static final String UPDATE_CONTACTS =
            "UPDATE computer_games_e_shop_storage.user_data " +
            "SET first_name=?, last_name=?, phone_number = ?, e_mail = ?, address = ? " +
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return id;
    }

    public boolean update(User user) {
        boolean result = false;
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
            e.printStackTrace();
        }

        return result;
    }

    public User getById(Long id) {
        User user = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (user == null) {
                    user = User.builder()
                            .id(resultSet.getLong("user_id"))
                            .login(resultSet.getString("login"))
                            .password(resultSet.getString("password"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .email(resultSet.getString("e_mail"))
                            .phoneNumber(resultSet.getString("phone_number"))
                            .role(Role.getByName(resultSet.getString("role")))
                            .address(resultSet.getString("address"))
                            .comments(new HashSet<>())
                            .build();
                }
                user.getComments().add(Comment.builder()
                        .id(resultSet.getLong("comment_id"))
                        .gameId(resultSet.getLong("game_id"))
                        .userId(resultSet.getLong("user_id"))
                        .text(resultSet.getString("text"))
                        .date(resultSet.getDate("date"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
