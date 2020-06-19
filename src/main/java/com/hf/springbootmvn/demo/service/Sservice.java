package com.hf.springbootmvn.demo.service;

import com.hf.springbootmvn.demo.entity.Account;
import com.hf.springbootmvn.demo.entity.AccountExample;
import com.hf.springbootmvn.demo.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sservice {
    @Autowired
    AccountMapper mapper;

    public boolean register(String loginName, String password) {
        if (loginName == null || password == null) {
            return false;
        }
        Account account = new Account();
        account.setLoginName(loginName);
        account.setPassword(password);
        int insert = mapper.insert(loginName, password);
        if (insert == 1) {
            return true;
        }
        return false;
    }

    public Account findByLoginNameAndPassword(String loginName, String password) {
        AccountExample example = new AccountExample();
        example.createCriteria().andLoginNameEqualTo(loginName);
        Account account = mapper.selectByLoginNameAndPassword(loginName, password);
        return account;
        /*List<Account> accounts = mapper.selectByExample(example);
        if (aaccounts.size() == 1){

        }*/

    }

    public List<Account> findAll() {
        AccountExample accountExample = new AccountExample();
        List<Account> accounts = mapper.selectByExample(accountExample);
        return accounts;
    }

    //修改密码
    public boolean updatePassword(String loginName, String password) {
        Account account = new Account();
        account.setPassword(password);
        account.setLoginName(loginName);
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andLoginNameEqualTo(loginName);
        if (mapper.updateByExampleSelective(account, accountExample) == 1) {
            return true;
        }
        return false;

    }

    public String deleteById(Integer id) {
        int i = mapper.deleteByPrimaryKey(id);
        if (i == 1) {
            return "success";
        } else {
            return "fail";
        }
    }

    public boolean save(Account account_object) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andLoginNameEqualTo(account_object.getLoginName());
        if (mapper.updateByExampleSelective(account_object, accountExample) == 1) {
            return true;
        }
        return false;
    }
}
