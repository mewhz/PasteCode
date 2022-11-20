package com.mewhz.paste.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.mewhz.paste.common.BaseResponse;
import com.mewhz.paste.common.ResultUtils;
import com.mewhz.paste.model.dto.UserLoginRequest;
import com.mewhz.paste.model.entity.User;
import com.mewhz.paste.model.vo.UserLoginResponse;
import com.mewhz.paste.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/findAll")
    public BaseResponse<List<User>> findAll() {
        return ResultUtils.success(userService.list());
    }

    @PostMapping("/login")
    public BaseResponse<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest) {

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        User user = userService.userLogin(userAccount, userPassword);
        UserLoginResponse userLoginResponse = new UserLoginResponse();

        System.out.println("user = " + user);
        if (user == null) {
            return ResultUtils.loginError("用户不存在!!");
        }
        else if (!user.getUserPassword().equals(userPassword)) {
            return ResultUtils.loginError("密码错误!!");
        }
        else if (user.getUserAccount().equals(userAccount) && user.getUserPassword().equals(userPassword)) {
            System.out.println(user);
            System.out.println("=====");
            BeanUtil.copyProperties(user, userLoginResponse);
            System.out.println(userLoginResponse);
            StpUtil.login(userLoginResponse.getId());
            System.out.println("StpUtil.isLogin() = " + StpUtil.isLogin());
            userLoginResponse.setTokenValue(StpUtil.getTokenInfo().getTokenValue());
            userLoginResponse.setTokenName(StpUtil.getTokenInfo().getTokenName());

            return ResultUtils.success(userLoginResponse);
        }
        else {
            return ResultUtils.error("未知错误, 请联系管理员!!");
        }
    }

    @GetMapping("/info")
    public boolean info() {
        return StpUtil.isLogin();
    }
}
