package vn.edu.iuh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.entity.Role;
import vn.edu.iuh.repository.RoleRepository;
import vn.edu.iuh.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role save(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }
}
