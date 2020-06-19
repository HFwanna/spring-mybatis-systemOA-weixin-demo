package com.hf.springbootmvn.demo.mapper;

import com.hf.springbootmvn.demo.entity.Account;
import com.hf.springbootmvn.demo.entity.AccountExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * AccountMapper继承基类
 */
@Repository
public interface AccountMapper extends MyBatisBaseDao<Account, Integer, AccountExample> {

    Account selectByLoginNameAndPassword(String loginName, String password);

    int insert(String loginName, String password);

//    int insertAll(Account account_object);
}