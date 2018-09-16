package project.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.connection.ConnectionPool;
import project.dto.gamedto.GameNameDto;
import project.dto.gamedto.NewGameDto;
import project.entity.DeveloperCompany;
import project.entity.Game;
import project.entity.enumonly.AgeLimit;
import project.entity.enumonly.GamePlatform;
import project.exception.DaoException;
import project.util.ListUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static project.util.ConstantUtil.BY_GENRE_SEARCH;
import static project.util.ConstantUtil.BY_PLATFORM_SEARCH;
import static project.util.ConstantUtil.BY_SUBGENRE_SEARCH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameDao {
    private static final GameDao INSTANCE = new GameDao();


    private static final String SAVE =
            "INSERT INTO computer_games_e_shop_storage.game (name, description, " +
                    "issue_year, minimal_system_requirements, " +
                    "recommended_system_requirements, image_url, age_limit) VALUES " +
                    "(?, ?,  " +
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
    private static final String GET_BY_ISSUE_YEAR =
            "SELECT id, name FROM computer_games_e_shop_storage.game WHERE issue_year = ?";
    private static final String ADD_SCREENSHOT =
            "INSERT INTO computer_games_e_shop_storage.game_screenshot (game_id, screenshot_url) VALUES (?, ?)";
    private static final String GET_ALL = "SELECT id, name, description, issue_year FROM computer_games_e_shop_storage.game";

    private static final Map<String, String> dataBaseQueries = new HashMap<>();

    static {
        dataBaseQueries.put(BY_PLATFORM_SEARCH, GET_BY_PLATFORM);
        dataBaseQueries.put(BY_SUBGENRE_SEARCH, GET_BY_SUBGENRE);
        dataBaseQueries.put(BY_GENRE_SEARCH, GET_BY_GENRE);
    }

    public List<Game> getAll(String sort) {
        List<Game> games = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL + sort);
            while (resultSet.next()) {
                games.add(Game.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .yearOfIssue(resultSet.getInt("issue_year"))
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return games;
    }

    public boolean addNewGame(NewGameDto newGame, DeveloperCompany company,
                              String[] subgenres, Map<GamePlatform, Integer> platforms,
                              Map<GamePlatform, Short> storage, String[] screenshots) {
        boolean isCompanyAdded;
        boolean subgenreSuccess = false;
        List<Long> storageId = new ArrayList<>();
        boolean screenshotSuccess = false;

        Long newGameId = save(newGame);

        Integer companyId;
        if (company.getId() == null) {
            companyId = DeveloperCompanyDao.getInstance().addNewDeveloperCompany(company.getName());
        } else {
            companyId = company.getId();
        }
        isCompanyAdded = DeveloperCompanyDao.getInstance().updateDeveloperCompany(companyId, newGameId);

        for (String subgenre : subgenres) {
            subgenreSuccess = SubgenreDao.getInstance().isSubgenreAdded(newGameId, Integer.valueOf(subgenre));
        }

        for (Map.Entry<GamePlatform, Integer> entry : platforms.entrySet()) {
            Long platformPriceId = GamePlatformDao.getInstance().setGamePlatform(newGameId, entry.getValue(), entry.getKey());
            storageId.add(StorageDao.getInstance().save(platformPriceId, storage.get(entry.getKey())));
        }

        for (String screenshot : screenshots) {
            screenshotSuccess = addScreenshot(newGameId, screenshot);
        }

        return isCompanyAdded && subgenreSuccess && !ListUtil.areValuesNull(storageId) && screenshotSuccess;
    }

    public Long save(NewGameDto newGame) {
        Long id = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newGame.getName());
            preparedStatement.setString(2, newGame.getDescription());
            preparedStatement.setInt(3, newGame.getYearOfIssue());
            preparedStatement.setString(4, newGame.getMinimalSystemRequirements());
            preparedStatement.setString(5, newGame.getRecommendedSystemRequirements());
            preparedStatement.setString(6, newGame.getImage());
            preparedStatement.setString(7, newGame.getAgeLimit().getName());

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

    public boolean addScreenshot(Long gameId, String url) {
        boolean result;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_SCREENSHOT)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, url);

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

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
             PreparedStatement preparedStatement = connection.prepareStatement(dataBaseQueries.get(queryReference))) {
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
                            .screenshots(new HashSet<>())
                            .build();
                }
                game.getGenre().add(resultSet.getString("genre_name"));
                game.getSubgenre().add(resultSet.getString("subgenre_name"));
                game.getPlatformPrice().put(GamePlatform.getByName(resultSet.getString("game_platform")),
                        resultSet.getInt("price"));
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
                            .image(resultSet.getString("image_url"))
                            .ageLimit(AgeLimit.getByName(resultSet.getString("age_limit")))
                            .genre(new HashSet<>())
                            .subgenre(new HashSet<>())
                            .platformPrice(new HashMap<>())
                            .screenshots(new HashSet<>())
                            .build();
                }
                game.getGenre().add(resultSet.getString("genre_name"));
                game.getSubgenre().add(resultSet.getString("subgenre_name"));
                game.getPlatformPrice().put(GamePlatform.getByName(resultSet.getString("game_platform")),
                        resultSet.getInt("price"));
                game.getScreenshots().add(resultSet.getString("screenshot_url"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return game;
    }

    public static GameDao getInstance() {
        return INSTANCE;
    }
}
