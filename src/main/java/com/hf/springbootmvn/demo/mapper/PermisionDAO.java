package com.hf.springbootmvn.demo.mapper;

import com.hf.springbootmvn.demo.entity.Permision;
import com.hf.springbootmvn.demo.entity.PermisionExample;
import org.springframework.stereotype.Repository;

/**
 * PermisionDAO继承基类
 */
@Repository
public interface PermisionDAO extends MyBatisBaseDao<Permision, Integer, PermisionExample> {
}