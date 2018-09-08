package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.GameDao;
import project.dto.gamedto.GameNameDto;
import project.entity.Game;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameService {

    private static final GameService INSTANCE = new GameService();

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

    public static GameService getInstance() {
        return INSTANCE;
    }
}
