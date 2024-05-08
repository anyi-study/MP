package com.itheima.mp.service;

import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class IUserServiceTest {
    @Autowired IUserService userService;

    @Test
    void testSaveUser() {
        User user = new User();
//        user.setId(5L);
        user.setUsername("CodeJ");
        user.setPassword("123");
        user.setPhone("18688990012");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
    }

    @Test
    void queryUser() {
        List<User> users = userService.listByIds(List.of(1L, 2L, 4l));
        users.forEach(System.out::println);
    }
}