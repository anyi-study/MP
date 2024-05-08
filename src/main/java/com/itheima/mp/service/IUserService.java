package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;

import java.util.List;

public interface IUserService extends IService<User> {
//    扣除用户金额
    void deductBalance(Long id, Integer money);
//多条件查询
    List<User> queryUsers(String name, Integer status, Integer maxBalance, Integer minBalance);
}
