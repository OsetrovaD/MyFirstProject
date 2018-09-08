package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GamePlatformService {

    private static final GamePlatformService INSTANCE = new GamePlatformService();

    public List<GamePlatform> getAll() {
        return Arrays.asList(GamePlatform.values());
    }

    public static GamePlatformService getInstance() {
        return INSTANCE;
    }
}
