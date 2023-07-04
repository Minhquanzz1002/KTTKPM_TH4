package vn.edu.iuh.service;

import vn.edu.iuh.entity.Role;

public interface RoleService {
    Role save(Role role);
    Role findById(Long id);
}
