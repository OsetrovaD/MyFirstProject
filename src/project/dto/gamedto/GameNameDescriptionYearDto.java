package project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameNameDescriptionYearDto {
    
    private Long id;
    private String name;
    private String description;
    private Integer issueYear;
}
