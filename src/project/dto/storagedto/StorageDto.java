package project.dto.storagedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageDto {

    String gameName;
    GamePlatform platform;
    Short number;
    LocalDate lastAdditionDate;
}
