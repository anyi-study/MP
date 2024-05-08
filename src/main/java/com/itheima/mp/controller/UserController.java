package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor //此注解会自动生成构造函数 但是你必须添加final
public class UserController {
//    由于不推荐使用Autowired注解，使用构造函数进行注入
    private final IUserService userService;
    @ApiOperation("新增用户接口")
    @PostMapping
//    @RequestBody JSON请求需要此注解
    public void saveUser(@RequestBody UserFormDTO userFormDTO){
        //1.把DTO拷贝到PO实体类
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
    }
    @ApiOperation("删除用户接口")
    @DeleteMapping("{id}")
    public void deleteUserById(@ApiParam("用户ID") @PathVariable("id") Long id){
        userService.removeById(id);
    }
    @ApiOperation("根据ID查询用户接口")
    @GetMapping("{id}")
    public UserVO queryUserById(@ApiParam("用户ID") @PathVariable("id") Long id){
        User user = userService.getById(id);
//        拷贝脱敏返回VO数据
        return BeanUtil.copyProperties(user, UserVO.class);
    }
    @ApiOperation("根据ID批量查询用户接口")
    @GetMapping
    public List<UserVO> queryUserByIds(@ApiParam("用户ID集合") @RequestParam("ids") List<Long> ids){
        List<User> users = userService.listByIds(ids);
//        拷贝脱敏返回VO数据
        return BeanUtil.copyToList(users, UserVO.class);
    }
    @ApiOperation("扣除用户余额接口")
    @DeleteMapping("/{id}/deduction/{money}")
    public void deductBalance(
            @ApiParam("用户ID") @PathVariable("id") Long id,
            @ApiParam("扣除金额") @PathVariable("money") Integer money){
        userService.deductBalance(id,money);
    }
    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> queryUsers(UserQuery userQuery){
        List<User> users = userService.queryUsers
                (userQuery.getName(),userQuery.getStatus(),userQuery.getMaxBalance(),userQuery.getMinBalance());
//        拷贝脱敏返回VO数据
        return BeanUtil.copyToList(users, UserVO.class);
    }
}
