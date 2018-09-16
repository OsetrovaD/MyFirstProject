package project.dto.gamedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.AgeLimit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewGameDto {

    private String name;
    private String description;
    private String image;
    private Integer yearOfIssue;
    private String minimalSystemRequirements;
    private String recommendedSystemRequirements;
    private AgeLimit ageLimit;
}
