package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.gamedto.GameNameDto;
import project.entity.Comment;
import project.entity.Game;
import project.entity.enumonly.AgeLimit;
import project.entity.enumonly.GamePlatform;
import project.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static project.util.ConstantUtil.BY_GENRE_SEARCH;
import static project.util.ConstantUtil.BY_PLATFORM_SEARCH;
import static project.util.ConstantUtil.BY_SUBGENRE_SEARCH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDao {
    private static final GameDao INSTANCE = new GameDao();

    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.game (name, description, company_id," +
                    "issue_year, minimal_system_requirements, " +
                    "recommended_system_requirements, image_url, age_limit) VALUES " +
                    "(?, ?, (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE UPPER(name) = UPPER(?)), " +
                    "?, ?, ?, ?, ?)";
    private static final String GET_BY_ID =
            "SELECT g.id, g.name as game_name, g.description, g.image_url, " +
                    "g.issue_year, g.minimal_system_requirements, " +
                    "g.recommended_system_requirements, g.age_limit, " +
                    "s2.name as subgenre_name, g2.name as genre_name, c.name as developer_name, " +
                    "pl.game_platform, pl.price, c2.id as comment_id, c2.user_id, c2.text, c2.date, gsc.screenshot_url " +
                    "FROM computer_games_e_shop_storage.game g " +
                    "JOIN computer_games_e_shop_storage.game_subgenre gs ON g.id = gs.game_id " +
                    "JOIN computer_games_e_shop_storage.subgenre s2 ON gs.subgenre_id = s2.id " +
                    "JOIN computer_games_e_shop_storage.genre g2 ON s2.genre_id = g2.id " +
                    "JOIN computer_games_e_shop_storage.game_game_platform pl ON g.id = pl.game_id " +
                    "JOIN computer_games_e_shop_storage.game_developer_company c ON g.company_id = c.id " +
                    "LEFT JOIN computer_games_e_shop_storage.game_screenshot gsc ON g.id = gsc.game_id " +
                    "LEFT JOIN computer_games_e_shop_storage.comment c2 on g.id = c2.game_id " +
                    "WHERE g.id = ?";
    private static final String GET_BY_NAME =
            "SELECT g.id, g.name as game_name, g.description, g.image_url, " +
                    "g.issue_year, g.minimal_system_requirements, " +
                    "g.recommended_system_requirements, g.age_limit, " +
                    "s2.name as subgenre_name, g2.name as genre_name, c.name as developer_name, " +
                    "pl.game_platform, pl.price, c2.id as comment_id, c2.user_id, c2.text, c2.date, gsc.screenshot_url " +
                    "FROM computer_games_e_shop_storage.game g " +
                    "JOIN computer_games_e_shop_storage.game_subgenre gs ON g.id = gs.game_id " +
                    "JOIN computer_games_e_shop_storage.subgenre s2 ON gs.subgenre_id = s2.id " +
                    "JOIN computer_games_e_shop_storage.genre g2 ON s2.genre_id = g2.id " +
                    "JOIN computer_games_e_shop_storage.game_game_platform pl ON g.id = pl.game_id " +
                    "JOIN computer_games_e_shop_storage.game_developer_company c ON g.company_id = c.id " +
                    "LEFT JOIN computer_games_e_shop_storage.game_screenshot gsc ON g.id = gsc.game_id " +
                    "LEFT JOIN computer_games_e_shop_storage.comment c2 on g.id = c2.game_id " +
                    "WHERE g.name = ?";
    private static final String GET_BY_GENRE =
            "SELECT g.id, g.name " +
                    "FROM computer_games_e_shop_storage.game g " +
                    "JOIN computer_games_e_shop_storage.game_subgenre gs ON g.id = gs.game_id " +
                    "JOIN computer_games_e_shop_storage.subgenre s2 ON gs.subgenre_id = s2.id " +
                    "JOIN computer_games_e_shop_storage.genre g2 ON s2.genre_id = g2.id " +
                    "WHERE UPPER(g2.name) = UPPER(?)";
    private static final String GET_BY_SUBGENRE =
            "SELECT g.id, g.name " +
                    "FROM computer_games_e_shop_storage.game g " +
                    "JOIN computer_games_e_shop_storage.game_subgenre gs ON g.id = gs.game_id " +
                    "JOIN computer_games_e_shop_storage.subgenre s2 ON gs.subgenre_id = s2.id " +
                    "WHERE UPPER(s2.name) = UPPER(?)";
    private static final String GET_BY_PLATFORM =
            "SELECT g.id, g.name " +
                    "FROM computer_games_e_shop_storage.game g " +
                    "JOIN computer_games_e_shop_storage.game_game_platform ggp  ON g.id = ggp.game_id " +
                    "WHERE UPPER(ggp.game_platform) = UPPER(?)";
    private static final String GET_BY_ISSUE_YEAR = "SELECT id, name FROM computer_games_e_shop_storage.game WHERE issue_year = ?";

    public List<GameNameDto> getByIssueYear(Integer issueYear) {
        List<GameNameDto> games = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ISSUE_YEAR)) {
            preparedStatement.setInt(1, issueYear);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                games.add(GameNameDto.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return games;
    }

    public List<GameNameDto> getByStringCharacteristic(String characteristic, String queryReference) {
        List<GameNameDto> games = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDatabaseQuery(queryReference))) {
            preparedStatement.setString(1, characteristic);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                games.add(GameNameDto.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return games;
    }

    public Game getByName(String name) {
        Game game = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (game == null) {
                    game = Game.builder()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("game_name"))
                            .description(resultSet.getString("description"))
                            .minimalSystemRequirements(resultSet.getString("minimal_system_requirements"))
                            .recommendedSystemRequirements(resultSet.getString("recommended_system_requirements"))
                            .companyDeveloper(resultSet.getString("developer_name"))
                            .yearOfIssue(resultSet.getInt("issue_year"))
                            .ageLimit(AgeLimit.getByName(resultSet.getString("age_limit")))
                            .image(resultSet.getString("image_url"))
                            .genre(new HashSet<>())
                            .subgenre(new HashSet<>())
                            .platformPrice(new HashMap<>())
                            .comment(new HashSet<>())
                            .screenshots(new HashSet<>())
                            .build();
                }
                game.getGenre().add(resultSet.getString("genre_name"));
                game.getSubgenre().add(resultSet.getString("subgenre_name"));
                game.getPlatformPrice().put(GamePlatform.getByName(resultSet.getString("game_platform")),
                        resultSet.getInt("price"));
                game.getComment().add(Comment.builder()
                        .id(resultSet.getLong("comment_id"))
                        .gameId(resultSet.getLong("id"))
                        .text(resultSet.getString("text"))
                        .userId(resultSet.getLong("user_id"))
                        .date(resultSet.getDate("date"))
                        .build());
                game.getScreenshots().add(resultSet.getString("screenshot_url"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return game;
    }

    public Game getById(Long id) {
        Game game = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (game == null) {
                    game = Game.builder()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("game_name"))
                            .description(resultSet.getString("description"))
                            .minimalSystemRequirements(resultSet.getString("minimal_system_requirements"))
                            .recommendedSystemRequirements(resultSet.getString("recommended_system_requirements"))
                            .companyDeveloper(resultSet.getString("developer_name"))
                            .yearOfIssue(resultSet.getInt("issue_year"))
                            .ageLimit(AgeLimit.getByName(resultSet.getString("age_limit")))
                            .image(resultSet.getString("image_url"))
                            .genre(new HashSet<>())
                            .subgenre(new HashSet<>())
                            .platformPrice(new HashMap<>())
                            .comment(new HashSet<>())
                            .screenshots(new HashSet<>())
                            .build();
                }
                game.getGenre().add(resultSet.getString("genre_name"));
                game.getSubgenre().add(resultSet.getString("subgenre_name"));
                game.getPlatformPrice().put(GamePlatform.getByName(resultSet.getString("game_platform")),
                        resultSet.getInt("price"));
                game.getComment().add(Comment.builder()
                        .id(resultSet.getLong("comment_id"))
                        .gameId(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .text(resultSet.getString("text"))
                        .date(resultSet.getDate("date"))
                        .build());
                game.getScreenshots().add(resultSet.getString("screenshot_url"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return game;
    }

    private String getDatabaseQuery(String parameter) {
        return setDatabaseQuery().get(parameter);
    }

    private Map<String, String> setDatabaseQuery() {
        Map<String, String> references = new HashMap<>();
        references.put(BY_PLATFORM_SEARCH, GET_BY_PLATFORM);
        references.put(BY_SUBGENRE_SEARCH, GET_BY_SUBGENRE);
        references.put(BY_GENRE_SEARCH, GET_BY_GENRE);
        return references;
    }

    public static GameDao getInstance() {
        return INSTANCE;
    }
}
