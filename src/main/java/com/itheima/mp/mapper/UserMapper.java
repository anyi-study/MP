package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.yaml.snakeyaml.scanner.Constant;

public interface UserMapper extends BaseMapper<User> {

    void updateBalanceByIds(@Param(Constants.WRAPPER) QueryWrapper<User> qw, @Param("amount") int amount);

    //扣除余额
//    可以注解写，也可以在mapper文件写sql
    @Update("update user set balance = balance - #{money} where id = #{id}")
    void deductBalance(@Param("id")Long id, @Param("money")Integer money);
}
