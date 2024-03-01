package com.gra.backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gra.backend.common.result.Result;
import com.gra.backend.dto.UserList;
import com.gra.backend.dto.token;
import com.gra.backend.mapper.DepartmentMapper;
import com.gra.backend.mapper.DoctorMapper;
import com.gra.backend.mapper.UserMapper;
import com.gra.backend.pojo.Department;
import com.gra.backend.pojo.Doctor;
import com.gra.backend.pojo.User;
import com.gra.backend.response.UserInfoRep;
import com.gra.backend.service.utils.UserDetailsImpl;
import com.gra.backend.utils.JwtUtil;
import com.gra.backend.utils.UserUtil;
import com.gra.backend.utils.redis.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gra.backend.common.constant.ResultCode.USER_HAS_EXISTED;
import static com.gra.backend.common.constant.UserRoleConstant.PATIENT_CODE;


@Service
@AllArgsConstructor
public class AccountService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private DoctorMapper doctorMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisUtil redisUtil;

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
        userMapper.insert(user);
        return Result.success();
    }

    public Result<?> getRole(UserList userList) {
        Integer role = Integer.valueOf(userList.getUserRole());
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // string list 转 int list stream
        List<Integer> list = Arrays.stream(userList.getUserList().toArray())
                .map(Object::toString)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        userLambdaQueryWrapper.in(User::getId, list);
        userLambdaQueryWrapper.ne(User::getRole, role);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);

        List<UserInfoRep> res = new ArrayList<>();
        if (Objects.equals(role, PATIENT_CODE)) {
            users.forEach(
                    nowUser -> {
                        UserInfoRep userInfoRep = new UserInfoRep();
                        BeanUtil.copyProperties(nowUser, userInfoRep);
                        LambdaQueryWrapper<Doctor> doctorLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        doctorLambdaQueryWrapper.eq(Doctor::getId, userInfoRep.getRoleId());
                        Doctor doctor = doctorMapper.selectOne(doctorLambdaQueryWrapper);
                        Integer departmentId = doctor.getDepartmentId();
                        LambdaQueryWrapper<Department> departmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        departmentLambdaQueryWrapper.eq(Department::getId, departmentId);
                        Department department = departmentMapper.selectOne(departmentLambdaQueryWrapper);
                        userInfoRep.setDepartmentName(department.getDepartmentName());
                        res.add(userInfoRep);
                    }
            );
        } else {
            users.forEach(
                    nowUser -> {
                        UserInfoRep userInfoRep = new UserInfoRep();
                        BeanUtil.copyProperties(nowUser, userInfoRep);
                        res.add(userInfoRep);
                    }
            );
        }
        return Result.success(res);
    }
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
