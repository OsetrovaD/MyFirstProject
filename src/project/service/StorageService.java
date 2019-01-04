package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.StorageDao;
import project.dto.storagedto.ChangeStorageNumberDto;
import project.dto.storagedto.CheckStorageDto;
import project.entity.Storage;
import project.entity.enumonly.GamePlatform;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageService {

    private static final StorageService INSTANCE = new StorageService();

    public List<Storage> getByGameId(Long id) {
        return StorageDao.getInstance().getByGameId(id);
    }

    public CheckStorageDto getByGameIdAndPlatform(GamePlatform platform, Long gameId) {
        Storage storage = StorageDao.getInstance().getByGameIdAndPlatform(platform, gameId);

        return CheckStorageDto.builder()
                .gameId(storage.getGameId())
                .platform(storage.getPlatform())
                .number(storage.getNumber())
                .build();
    }

    public boolean update(ChangeStorageNumberDto gameData, Short number, String query) {
        return StorageDao.getInstance().update(gameData, number, query);
    }

    public static StorageService getInstance() {
        return INSTANCE;
    }
}
