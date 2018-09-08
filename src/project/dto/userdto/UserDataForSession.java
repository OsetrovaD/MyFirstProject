package project.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataForSession {

    private Long id;
    private String login;
    private Role role;
}
