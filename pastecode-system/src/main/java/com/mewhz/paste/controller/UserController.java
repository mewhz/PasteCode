package com.mewhz.paste.controller;


import com.mewhz.paste.common.BaseResponse;
import com.mewhz.paste.common.ResultUtils;
import com.mewhz.paste.model.dto.UserLoginRequest;
import com.mewhz.paste.model.entity.User;
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
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest) {

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        User user = userService.userLogin(userAccount, userPassword);

        System.out.println("user = " + user);
        if (user == null) {
            return ResultUtils.loginError("用户不存在!!");
        }
        else if (!user.getUserPassword().equals(userPassword)) {
            return ResultUtils.loginError("密码错误!!");
        }
        else if (user.getUserAccount().equals(userAccount) && user.getUserPassword().equals(userPassword)) {
            return ResultUtils.success(user);
        }
        else {
            return ResultUtils.error();
        }
    }
}
