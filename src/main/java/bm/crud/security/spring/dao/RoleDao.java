package bm.crud.security.spring.dao;

import bm.crud.security.spring.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleDao {
    @Transactional(readOnly = true)
    List<Role> findAll();
    @Transactional(readOnly = true)
    Role findByRoleNameOrNull(String roleName);
//    Role getNewRole();
}
