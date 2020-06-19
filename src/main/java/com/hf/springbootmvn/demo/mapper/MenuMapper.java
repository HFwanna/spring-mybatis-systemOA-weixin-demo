package com.hf.springbootmvn.demo.mapper;

import com.hf.springbootmvn.demo.entity.Menu;
import com.hf.springbootmvn.demo.entity.MenuExample;
import org.springframework.stereotype.Repository;

/**
 * MenuMapper继承基类
 */
@Repository
public interface MenuMapper extends MyBatisBaseDao<Menu, Integer, MenuExample> {
}