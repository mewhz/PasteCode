package com.mewhz.paste.controller;


import com.mewhz.paste.model.dto.UserSearchVO;
import com.mewhz.paste.model.entity.User;
import com.mewhz.paste.model.vo.*;
import com.mewhz.paste.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResultVO<UserInfoVO> userLogin(@RequestBody UserLoginVO userLoginVO) {
        return ResultVO.ok(userService.login(userLoginVO));
    }

    @GetMapping("/logout")
    public ResultVO<?> logout() {
        return ResultVO.ok();
    }

    @PostMapping("/register")
    public ResultVO<UserInfoVO> register(@RequestBody UserRegisterVO userRegisterVO) {

        return ResultVO.ok(userService.register(userRegisterVO));
    }

    @GetMapping("/info/{account}")
    public ResultVO<UserInfoVO> getUserInfo(@PathVariable String account) {
        return ResultVO.ok(userService.getUserInfo(account));
    }

    @GetMapping("/list")
    public ResultVO<ResultPageVO<UserInfoVO>> getList(UserSearchVO userSearchVO) {
        return ResultVO.ok(userService.getList(userSearchVO));
    }

    @PostMapping("/update")
    public ResultVO<Boolean> update(@RequestBody User user) {
        return ResultVO.ok(userService.updateById(user));
    }

    @PostMapping("/delete")
    public ResultVO<Boolean> delete(@RequestBody User user) {
        return ResultVO.ok(userService.removeById(user));
    }

    @PostMapping("/upload")
    public ResultVO<String> upload(MultipartFile file) {
        return ResultVO.ok(file.getOriginalFilename());
    }

    @GetMapping("/random")
    public ResultVO<String> random() {
        return ResultVO.ok(userService.randomUserAccount());
    }

    @GetMapping("/getUserId/{userAccount}")
    public ResultVO<Integer> userAccountGetUserId(@PathVariable String userAccount) {
        return ResultVO.ok(userService.userAccountGetUserId(userAccount));
    }
}
