package com.gra.backend.controller;

import com.gra.backend.common.result.Result;
import com.gra.backend.dto.UserList;
import com.gra.backend.pojo.User;
import com.gra.backend.service.AccountService;
import com.gra.backend.utils.redis.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@AllArgsConstructor
@RequestMapping("/api/user/account")

public class AccountController {

    private final AccountService accountService;
    @Resource
    private RedisUtil redisUtil;


    @PostMapping("token") //账号密码获取token
    public Result<?> getToken(User user) {
        return accountService.getToken(user);
    }

    @GetMapping("info")
    public Result<?> getInfo() {
        return accountService.getInfo();
    }

    @PostMapping("register")
    public Result<?> register(User user) {
        return accountService.register(user);
    }


    @PostMapping("getRole")
    public Result<?> getRole(UserList userList) {
        return accountService.getRole(userList);
    }


//    @PostMapping("emailtoken")  //邮箱code获取token
//    public Result<?> getEmailToken(@RequestParam Map<String, String> map) {
//        String email = map.get("email");
//        String code = map.get("code");
//        return accountService.getEmailToken(email, code);
//    }
}
