package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.GameDao;
import project.dto.gamedto.GameNameDescriptionYearDto;
import project.dto.gamedto.GameNameDto;
import project.dto.gamedto.GameWithPricesDto;
import project.dto.gamedto.NamePlatformsDto;
import project.dto.gamedto.NewGameDto;
import project.entity.DeveloperCompany;
import project.entity.Game;
import project.entity.enumonly.GamePlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameService {

    private static final GameService INSTANCE = new GameService();

    public List<GameWithPricesDto> getWithPrices() {
        return GameDao.getInstance().getAllWithPrices().stream()
                .map(game -> GameWithPricesDto.builder()
                                        .name(game.getName())
                                        .prices(game.getPlatformPrice())
                                        .build())
                .collect(Collectors.toList());
    }
    
    public List<GameNameDescriptionYearDto> getAll(String sort) {
        List<GameNameDescriptionYearDto> games = new ArrayList<>();

        GameDao.getInstance().getAll(sort)
                .forEach(g -> games.add(GameNameDescriptionYearDto.builder()
                                        .id(g.getId())
                                        .name(g.getName())
                                        .description(g.getDescription())
                                        .issueYear(g.getYearOfIssue())
                                        .build()));
        
        return games;
    }

    public List<NamePlatformsDto> getAllWithPlatforms() {
        return GameDao.getInstance().getAllWithPlatforms();
    }

    public Game getById(Long id) {
        return GameDao.getInstance().getById(id);
    }

    public List<GameNameDto> getByCharacteristic(String characteristic, String reference) {
        return GameDao.getInstance().getByStringCharacteristic(characteristic, reference);
    }

    public Game getByName(String name) {
        return GameDao.getInstance().getByName(name);
    }

    public List<GameNameDto> getByIssueYear(Integer year) {
        return GameDao.getInstance().getByIssueYear(year);
    }

    public boolean save(NewGameDto newGame, DeveloperCompany company,
                        String[] subgenres, Map<GamePlatform, Integer> platforms,
                        Map<GamePlatform, Short> storage, List<String> screenhots) {
        return GameDao.getInstance().addNewGame(newGame, company, subgenres, platforms, storage, screenhots);
    }

    public static GameService getInstance() {
        return INSTANCE;
    }
}
