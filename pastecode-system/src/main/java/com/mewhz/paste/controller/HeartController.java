package com.mewhz.paste.controller;

import com.mewhz.paste.service.CodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/heart")
public class HeartController {

    @Resource
    private CodeService codeService;


//    @GetMapping("/login")
//    public SaTokenInfo login() {
//        StpUtil.login(1001);
//        return StpUtil.getTokenInfo();
//    }
//
//    @GetMapping("/info")
//    public boolean isLogin() {
//        return StpUtil.isLogin();
//    }
//
//    @GetMapping("/logout")
//    public String logout(){
//        StpUtil.logout();
//         return "success";
//    }
//
//    @SaCheckLogin
//    @GetMapping("/token")
//    public String token() {
//        return "Hello World";
//    }

//    @Resource
//    UserService userService;
//
//    @GetMapping("/")
//    public void fakerUser() {
//
//        for (int i = 0; i < 20; i ++) {
//            UserRegisterVO userRegisterVO = new UserRegisterVO();
//
//            Faker faker = new Faker();
//
//
//            userRegisterVO.setUserName(faker.random().hex());
//            userRegisterVO.setUserEmail(faker.random().hex() + "@163.com");
//            userRegisterVO.setUserPassword("123456");
//
//            userService.register(userRegisterVO);
//        }
//
//    }

}
