package project.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entity.enumonly.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForAdminDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private Role role;
}
