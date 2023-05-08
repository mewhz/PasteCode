package com.mewhz.paste.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.exception.BizException;
import com.mewhz.paste.mapper.LogMapper;
import com.mewhz.paste.mapper.UserMapper;
import com.mewhz.paste.model.dto.UserSearchVO;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.entity.User;
import com.mewhz.paste.model.vo.ResultPageVO;
import com.mewhz.paste.model.vo.UserInfoVO;
import com.mewhz.paste.model.vo.UserLoginVO;
import com.mewhz.paste.model.vo.UserRegisterVO;
import com.mewhz.paste.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mewhz.paste.constant.UserConstant.*;
import static com.mewhz.paste.constant.LogConstant.*;

/**
 * @author mewhz
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private LogMapper logMapper;


    public UserInfoVO login(UserLoginVO userLoginVO) {

        String userAccount = userLoginVO.getUserAccount();
        String userPassword = userLoginVO.getUserPassword();

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                        .eq(User::getUserAccount, userAccount));

        Log log = new Log();

        if (user == null) {

            log.setLogInfo(userLoginVO.toString());
            log.setLogType(LOGIN);
            log.setLogIsSuccess(false);

            this.logMapper.insert(log);

            throw new BizException("用户不存在!");
        }
        else if (!user.getUserPassword().equals(userPassword)) {

            log.setLogInfo(userLoginVO.toString());
            log.setLogType(LOGIN);
            log.setLogIsSuccess(false);

            this.logMapper.insert(log);

            throw new BizException("密码错误!");
        }
        else if (user.getUserAccount().equals(userAccount) && user.getUserPassword().equals(userPassword)) {

            UserInfoVO userInfoVO = new UserInfoVO();

            BeanUtils.copyProperties(user, userInfoVO);


            if (user.getUserRole() == 1) {
                userInfoVO.setUserRoleStr("管理员");
            }
            else {
                userInfoVO.setUserRoleStr("普通用户");
            }

            log.setLogInfo(userLoginVO.toString());
            log.setLogType(LOGIN);
            log.setLogIsSuccess(true);

            this.logMapper.insert(log);

            return userInfoVO;
        }
        else {

            log.setLogInfo(userLoginVO.toString());
            log.setLogType(LOGIN);
            log.setLogIsSuccess(false);

            this.logMapper.insert(log);

            throw new BizException("未知错误, 请联系管理员!");
        }
    }

    public UserInfoVO register(UserRegisterVO userRegisterVO) {

        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                        .eq(User::getUserEmail, userRegisterVO.getUserEmail()));

        Log log = new Log();

        if (existUser != null) {

            log.setLogInfo(userRegisterVO.toString());
            log.setLogType(REGISTER);
            log.setLogIsSuccess(false);

            this.logMapper.insert(log);

            throw new BizException("邮箱已存在!");
        }

        User user = new User();

        BeanUtil.copyProperties(userRegisterVO, user);

        user.setUserCodeNum(0);
        user.setUserAvatar("avatar.jpg");
        user.setUserRole(0);

        String userAccount = "";

        for (int i = 0; i < ACCOUNT_RETRY_CYCLE; i ++) {

             userAccount = UserUtils.userAccountGenerate();

            existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                            .eq(User::getUserAccount, userAccount));

            if (existUser == null) {
                break;
            }
        }

        user.setUserAccount(userAccount);

        System.out.println(user);

        this.save(user);

        UserInfoVO userInfoVO = new UserInfoVO();

        BeanUtil.copyProperties(user, userInfoVO);


        log.setLogInfo(userRegisterVO.toString());
        log.setLogType(REGISTER);
        log.setLogIsSuccess(true);

        this.logMapper.insert(log);

        return userInfoVO;
    }

    public UserInfoVO getUserInfo (String userAccount) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                        .eq(User::getUserAccount, userAccount));

        System.out.println("user = " + user);
        UserInfoVO userInfoVO = new UserInfoVO();

        BeanUtil.copyProperties(user, userInfoVO);

        System.out.println("userInfoVO = " + userInfoVO);

        return userInfoVO;
    }

    public ResultPageVO<UserInfoVO> getList (UserSearchVO userSearchVO) {

        Integer count = userMapper.userTotal(userSearchVO);
        List<User> userList = userMapper.userList(userSearchVO, USER_PAGE_NUM);

        List<UserInfoVO> userInfoVOList = new ArrayList<>();

        for (User user : userList) {
            UserInfoVO userinfo = new UserInfoVO();

            BeanUtil.copyProperties(user, userinfo);

            if (user.getUserRole() == 1) {
                userinfo.setUserRoleStr("管理员");
            }
            else {
                userinfo.setUserRoleStr("普通用户");
            }

            userInfoVOList.add(userinfo);
        }

        return new ResultPageVO<>(userInfoVOList, count);
    }

    public String randomUserAccount(){
        List<User> users = this.list();

        return users.get(RandomUtil.randomInt(0, users.size())).getUserAccount();
    }

    public Integer userAccountGetUserId(String userAccount) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userAccount));

        return user != null ? user.getUserId() : -1;

    }

    public Boolean updateUser(User user) {

        List<User> list = this.list(new LambdaQueryWrapper<User>()
                .eq(User::getUserEmail, user.getUserEmail()));

        Log log = new Log();

        for (User u : list) {
            if (!Objects.equals(u.getUserId(), user.getUserId())) {

                log.setLogInfo(user.toString());
                log.setLogType(UPDATE_USER);
                log.setLogIsSuccess(false);

                this.logMapper.insert(log);

                throw new BizException("邮箱已存在!");
            }
        }

        log.setLogInfo(user.toString());
        log.setLogType(UPDATE_USER);
        log.setLogIsSuccess(true);

        this.logMapper.insert(log);

        return this.updateById(user);
    }

    public Boolean deleteUser(User user) {

        Log log = new Log();

        log.setLogInfo(user.toString());
        log.setLogType(DELETE_USER);
        log.setLogIsSuccess(true);

        this.logMapper.insert(log);

        return this.removeById(user);
    }

}




