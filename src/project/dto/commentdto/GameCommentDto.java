package project.dto.commentdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.dto.userdto.UserLoginIdDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameCommentDto {

    private UserLoginIdDto user;
    private String text;
    private LocalDate date;
}
