package project.dto.storagedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

@Data
@EqualsAndHashCode(of = {"gameId", "platform"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckStorageDto {

    Long gameId;
    GamePlatform platform;
    Short number;
}
