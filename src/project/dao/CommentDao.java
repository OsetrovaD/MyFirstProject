package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.entity.Comment;

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
    private static final String GET_BY_ID = "SELECT id, game_id, text, date, user_id " +
            "FROM computer_games_e_shop_storage.comment WHERE id = ?";
    private static final String GET_BY_USER_ID = "SELECT id, game_id, text, date, user_id " +
            "FROM computer_games_e_shop_storage.comment WHERE user_id = ?";
    private static final String GET_BY_GAME_ID = "SELECT id, game_id, text, date, user_id " +
            "FROM computer_games_e_shop_storage.comment WHERE game_id = ?";
    private static final String SAVE = "INSERT INTO computer_games_e_shop_storage.comment " +
            "(game_id, text, date, user_id) VALUES " +
            "(?, ?, now(), ?)";

    public static Comment getById(Long id) {
        Comment comment = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                comment = Comment.builder()
                        .id(resultSet.getLong("id"))
                        .gameId(resultSet.getLong("game_id"))
                        .userId(resultSet.getLong("user_id"))
                        .text(resultSet.getString("text"))
                        .date(resultSet.getDate("date"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comment;
    }

    public List<Comment> getByUserId(Long userId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(Comment.builder()
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

        return comments;
    }

    public Long save(Comment comment) {
        Long id = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, comment.getGameId());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setLong(3, comment.getUserId());

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong("id");
                comment.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static CommentDao getInstance() {
        return INSTANCE;
    }
}
