package bm.crud.security.spring.service;

import bm.crud.security.spring.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {
    @Transactional(readOnly = true)
    List<Role> findAll();
}
