package project.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.dao.UserDao;
import project.entity.enumonly.Role;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleService {

    private static final RoleService INSTANCE = new RoleService();

    public List<Role> getAll() {
        return Arrays.asList(Role.values());
    }

    public boolean updateRole(Long userId, Role role) {
        return UserDao.getInstance().updateRole(userId, role);
    }

    public static RoleService getInstance() {
        return INSTANCE;
    }
}
