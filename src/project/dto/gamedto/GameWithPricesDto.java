package project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameWithPricesDto {

    private String name;
    private Map<GamePlatform, Integer> prices;
}
