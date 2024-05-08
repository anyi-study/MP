package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    /**
     * 扣除用户金额
     * @param id
     * @param money
     */
    @Override
    public void deductBalance(Long id, Integer money) {
        //1.查询用户
//        第一个方法 调用自己
        User user = this.getById(id);
        //2.校验用户状态
        if (user == null || user.getStatus() == 2){
            throw new RuntimeException("用户不存在或已冻结");
        }
        //3.校验用户余额是否大于扣除余额
        if (user.getBalance()<money){
            throw new RuntimeException("余额不足");
        }
//        剩余余额
        int remainBalance = user.getBalance() - money;
        //4.扣除余额
        lambdaUpdate()
                .set(User::getBalance,remainBalance)
                .set(remainBalance == 0,User::getStatus,2)//remainBalance == 0 这个是条件
                .eq(User::getId,id)
                .eq(User::getBalance,user.getBalance())
                .update();
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer maxBalance, Integer minBalance) {
        return lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(maxBalance != null, User::getBalance, maxBalance)
                .le(minBalance != null, User::getBalance, minBalance)
                .list();
    }
}
