package com.mewhz.paste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.UserMapper;
import com.mewhz.paste.model.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author mewhz
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public User userLogin(String userAccount, String userPassword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("user_account", userAccount);

        return this.getOne(queryWrapper);
    }
}
