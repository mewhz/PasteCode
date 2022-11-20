package com.mewhz.paste.controller;

import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.User;
import com.mewhz.paste.model.vo.ResultVO;
import com.mewhz.paste.model.vo.UserRegisterVO;
import com.mewhz.paste.service.CodeService;
import com.mewhz.paste.service.UserService;
import lombok.SneakyThrows;
import net.datafaker.Faker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @SneakyThrows
    @GetMapping("/download/{codeId}")
    public void download(HttpServletResponse response, @PathVariable String codeId) {
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition",
                "attachment;filename=file_" + System.currentTimeMillis() + ".txt");

        Code code = codeService.getById(codeId);

        byte[] bytes = code.getCodeText().getBytes();

        System.out.println(bytes.length);

        response.getOutputStream().write(bytes);

    }
}
