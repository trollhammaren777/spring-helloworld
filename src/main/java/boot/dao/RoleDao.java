package boot.dao;

import boot.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleDao {
    @Transactional(readOnly = true)
    List<Role> findAll();
    @Transactional(readOnly = true)
    Role findByRoleNameOrNull(String roleName);
}