package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.UserDao;
import project.dto.userdto.UserDataDto;
import project.dto.userdto.NewUserDto;
import project.dto.userdto.UserDataForSession;
import project.dto.userdto.UserDto;
import project.dto.userdto.UserForAdminDto;
import project.entity.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    public UserDataForSession getByLoginAndPassword(UserDto userDto) {
        return UserDao.getInstance().getByLoginAndPassword(userDto);
    }

    public UserDataForSession getById(Long userId) {
        return UserDao.getInstance().getById(userId);
    }

    public UserDataDto getInfo(Long userId) {
        User user = UserDao.getInstance().getInfo(userId);
        return UserDataDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public boolean updateData(Long userId, String parameter, String value) {
        return UserDao.getInstance().updateData(userId, parameter, value);
    }

    public List<UserForAdminDto> getAll() {
        return UserDao.getInstance().getAll();
    }

    public Long save(NewUserDto newUser) {
        User tempUser = User.builder()
                .login(newUser.getLogin())
                .password(newUser.getPassword())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .phoneNumber(newUser.getPhoneNumber())
                .address(newUser.getAddress())
                .build();
        return UserDao.getInstance().save(tempUser);
    }

    public boolean update(UserDataDto changes, Long id) {
        User tempUser = User.builder()
                .id(id)
                .firstName(changes.getFirstName())
                .lastName(changes.getLastName())
                .email(changes.getEmail())
                .phoneNumber(changes.getPhoneNumber())
                .address(changes.getAddress())
                .build();
        return UserDao.getInstance().update(tempUser);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
