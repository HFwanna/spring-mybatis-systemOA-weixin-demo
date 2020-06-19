package com.hf.springbootmvn.demo.service;

import com.hf.springbootmvn.demo.entity.*;
import com.hf.springbootmvn.demo.mapper.PermisionDAO;
import com.hf.springbootmvn.demo.mapper.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleDAO roleDAO;

    @Autowired
    PermisionDAO permisionDAO;

    public List<Role> findAll() {
        RoleExample roleExample = new RoleExample();
        List<Role> roles = roleDAO.selectByExample(roleExample);
        return roles;
    }

    public Role findById(Integer id) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdEqualTo(id);
        Role role = roleDAO.selectByPrimaryKey2(id);
        return role;
    }

    public Role updateRole(Role role) {
        int i = roleDAO.updateByPrimaryKey2(role);
        if (i == 1) {
            return role;
        }
        return null;
    }

    public List<Permision> findPermisionById(int id) {
        PermisionExample permisionExample = new PermisionExample();
        List<Permision> permisions = permisionDAO.selectByExample(permisionExample);
        return permisions;
    }
}
