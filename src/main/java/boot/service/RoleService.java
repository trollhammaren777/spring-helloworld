package boot.service;

import boot.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {
    @Transactional(readOnly = true)
    List<Role> findAll();
    @Transactional(readOnly = true)
    Role findByRoleNameOrNull(String roleName);
}
