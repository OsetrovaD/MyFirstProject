package project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GamersNumber {
    SINGLEPLAYER("Однопользовательская игра"),
    MULTIPLAYER("Многопользовательская игра"),
    MMOG("Многопользовательская онлайн-игра"),
    COOP("Кооперативный режим");

    private String name;

    GamersNumber(String name) {
        this.name = name;
    }

    public static GamersNumber getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
