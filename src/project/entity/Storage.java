package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(of = {"gameId", "platform"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Storage {

    private Long gameId;
    private GamePlatform platform;
    private Short number;
    private LocalDate lastAdditionDate;
}
