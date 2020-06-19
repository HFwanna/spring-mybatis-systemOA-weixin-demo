package com.hf.springbootmvn.demo.mapper;

import com.hf.springbootmvn.demo.entity.Role;
import com.hf.springbootmvn.demo.entity.RoleExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoleDAO继承基类
 */
@Repository
public interface RoleDAO extends MyBatisBaseDao<Role, Integer, RoleExample> {
    Role selectByPrimaryKey2(Integer id);

    int updateByPrimaryKey2(Role role);
}