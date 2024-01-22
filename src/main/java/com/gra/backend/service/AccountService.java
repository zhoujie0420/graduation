package com.gra.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gra.backend.common.result.Result;
import com.gra.backend.dto.token;
import com.gra.backend.mapper.UserMapper;
import com.gra.backend.pojo.User;
import com.gra.backend.service.utils.UserDetailsImpl;
import com.gra.backend.utils.JwtUtil;
import com.gra.backend.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.gra.backend.common.constant.ResultCode.USER_HAS_EXISTED;


@Service
@AllArgsConstructor
public class AccountService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;

    public Result<?> getToken(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken); //登录失败会自己处理
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User loginuser = loginUser.getUser(); // 取出user
        String jwt = JwtUtil.createJWT(loginuser.getId().toString());

        return Result.success(new token(jwt));
    }

    public Result<?> getInfo() {
        User user = UserUtil.getUser();
        user.setPassword(null);
        return Result.success(user);
    }

    public Result<?> register(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        if (!users.isEmpty()) {
            return Result.fail(USER_HAS_EXISTED.getMessage());
        }

        User res = new User(null, user.getUsername(), user.getPassword(), user.getAge(), user.getPhone(), user.getRole(), user.getRoleId());
        userMapper.insert(user);
        return Result.success();
    }


    //    public Result<?> getEmailToken(String email, String code) { //邮箱登录
//        Map<String, String> map = new HashMap<>();
//        if (!redisUtil.hasKey(email)) {
//            System.out.println("不存在这个email");
//            map.put("error_message", "邮箱错误");
//            return map;
//        }
//        // 强制转化object会报  java.lang.Integer cannot be cast to java.lang.String 错误
//        // String.valueOf 实质调用 object.toString() 方法
//        String getcode = String.valueOf(redisUtil.get(email));
//        System.out.println(getcode);
//        System.out.println(code);
//        if (!Objects.equals(code, getcode)) {
//            map.put("error_message", "验证码错误");
//            return map;
//        }
//        //下面是成功匹配后的操作
//        //需要将 redis 的 key 删除
//        redisUtil.del(email);
//
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("email", email);
//        User user = userMapper.selectOne(queryWrapper);
//
//        //生成jwt (通过id)
//        String jwt = JwtUtil.createJWT(user.getId().toString());
//
//        map.put("error_message", "success");
//        map.put("token", jwt);
//        return map;
//    }

}
