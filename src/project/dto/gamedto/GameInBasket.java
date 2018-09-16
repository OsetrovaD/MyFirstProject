package project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

@Data
@EqualsAndHashCode(of = {"id", "platform"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameInBasket {

    Long id;
    String name;
    GamePlatform platform;
    Integer price;
    Integer number;
}
