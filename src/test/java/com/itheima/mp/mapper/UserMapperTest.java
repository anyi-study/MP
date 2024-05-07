package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }
    @Test
    void testQueryWrapper(){
        QueryWrapper<User> qw = new QueryWrapper<User>()
                .select("id","username","info","balance")
                .like("username","o")
                .ge("balance",1000);
        List<User> users = userMapper.selectList(qw);
        users.forEach(System.out::println);
    }
    @Test
    void testUpdateQueryWrapper(){
        User user = new User();
        user.setBalance(2000);
        QueryWrapper<User> qw = new QueryWrapper<User>()
                .eq("username","jack");
        userMapper.update(user,qw);
    }
    @Test
    void testUpdateWrapper(){
        List<Long> ids = List.of(1L, 2L, 4L);
        UpdateWrapper<User> uw = new UpdateWrapper<User>()
                .setSql("balance = balance-200") //拼接语句
                .in("id",ids);
        userMapper.update(null,uw);
    }
    @Test
    void testLambdaQueryWrapper(){
        List<Long> ids = List.of(1L, 2L, 4L);
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<User>()
                .select(User::getId,User::getUsername,User::getInfo,User::getBalance)
                .like(User::getUsername,"o")
                .ge(User::getBalance,1000);
        List<User> users = userMapper.selectList(qw);
        users.forEach(System.out::println);
    }
    @Test
    void testCustomSqlUpdate(){
        List<Long> ids = List.of(1L, 2L, 4L);
        int amount = 200;
        QueryWrapper<User> qw = new QueryWrapper<User>().in("id",ids);
//        调用自定义方法
        userMapper.updateBalanceByIds(qw,amount);
    }
}