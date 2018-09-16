package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.commentdto.GameCommentDto;
import project.dto.commentdto.NewCommentDto;
import project.dto.commentdto.UserCommentDto;
import project.dto.gamedto.GameNameDto;
import project.dto.userdto.UserLoginIdDto;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDao {

    private static final CommentDao INSTANCE = new CommentDao();
    private static final String GET_BY_USER_ID = "SELECT c.text, c.date, g.name, g.id " +
            "FROM computer_games_e_shop_storage.comment c " +
            "JOIN computer_games_e_shop_storage.game g on c.game_id = g.id " +
            "WHERE user_id = ?";
    private static final String GET_BY_GAME_ID = "SELECT c.text, c.date, u.login, u.id " +
            "FROM computer_games_e_shop_storage.comment c " +
            "JOIN computer_games_e_shop_storage.user_data u on c.user_id = u.id " +
            "WHERE game_id = ?";
    private static final String SAVE = "INSERT INTO computer_games_e_shop_storage.comment " +
            "(game_id, text, date, user_id) VALUES " +
            "(?, ?, now(), ?)";

    public List<UserCommentDto> getByUserId(Long id) {
        List<UserCommentDto> comments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(UserCommentDto.builder()
                        .game(GameNameDto.builder()
                                .name(resultSet.getString("name"))
                                .id(resultSet.getLong("id"))
                                .build())
                        .text(resultSet.getString("text"))
                        .date(resultSet.getDate("date").toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return comments;
    }

    public List<GameCommentDto> getByGameId(Long id) {
        List<GameCommentDto> comments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_GAME_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(GameCommentDto.builder()
                        .user(UserLoginIdDto.builder()
                                .id(resultSet.getLong("id"))
                                .login(resultSet.getString("login"))
                                .build())
                        .text(resultSet.getString("text"))
                        .date(resultSet.getDate("date").toLocalDate())
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return comments;
    }

    public Long save(NewCommentDto comment) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, comment.getGameId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setLong(3, comment.getUserId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
                comment.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return id;
    }

    public static CommentDao getInstance() {
        return INSTANCE;
    }
}
