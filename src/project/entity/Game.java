package project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entity.enumonly.AgeLimit;
import project.entity.enumonly.GamePlatform;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    private Long id;
    private String name;
    private String description;
    private String companyDeveloper;
    private String image;
    private Integer yearOfIssue;
    private String minimalSystemRequirements;
    private String recommendedSystemRequirements;
    private AgeLimit ageLimit;
    private Set<String> genre;
    private Set<String> subgenre;
    private Map<GamePlatform, Integer> platformPrice;
    private Set<String> screenshots;
}
