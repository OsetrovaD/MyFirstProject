package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInOrderDto {

    private Long gameId;
    private String gameName;
    private GamePlatform platform;
    private Integer number;
    private Integer price;
}
