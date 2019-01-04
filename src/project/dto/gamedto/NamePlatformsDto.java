package project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.GamePlatform;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NamePlatformsDto {

    private Long id;
    private String name;
    private List<GamePlatform> platforms;
}
