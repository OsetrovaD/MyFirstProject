package project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
@ToString
public class GameNameDto {

    private Long id;
    private String name;
}
