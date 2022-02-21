package boot.service;

import boot.dao.RoleDao;
import boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByRoleNameOrNull(String roleName) {
        return roleDao.findByRoleNameOrNull(roleName);
    }
}
