package project.dto.storagedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStorageNumberDto {

    Long gameId;
    GamePlatform platform;
}
