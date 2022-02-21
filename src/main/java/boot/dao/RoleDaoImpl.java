package boot.dao;

import boot.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final EntityManager entityManager;
    private List<Role> rolesList = new ArrayList<>();

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByRoleNameOrNull(String roleName) {
        List<Role> tempUserRolesList = new ArrayList<>();
        Role userRole = null;
        try {
            entityManager.getTransaction().begin();
            tempUserRolesList = (List<Role>) entityManager.createQuery(
                    "SELECT r FROM Role r WHERE r.roleName =: roleName")
                    .setParameter("roleName", roleName)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        if (tempUserRolesList != null && !tempUserRolesList.isEmpty()) {
            userRole = tempUserRolesList.get(0);
        }

        return userRole;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findAll() {
        rolesList.clear();
        Role roleUser;
        Role roleAdmin;
        try {
            entityManager.getTransaction().begin();
            rolesList = (List<Role>) entityManager
                            .createQuery("SELECT r FROM Role r")
                            .getResultList();
            if (rolesList.isEmpty()) {
                roleUser = new Role(1L, "USER");
                roleAdmin = new Role(2L, "ADMIN");
                entityManager.merge(roleUser);
                entityManager.merge(roleAdmin);
                rolesList.add(roleUser);
                rolesList.add(roleAdmin);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }

        return rolesList;
    }
}