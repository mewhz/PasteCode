package com.mewhz.paste.utils;

import cn.hutool.core.util.RandomUtil;
import com.mewhz.paste.constant.UserConstant;
import com.mewhz.paste.service.UserService;

import javax.annotation.Resource;

/**
 * @author mewhz
 */
public class UserUtils {


    public static String userAccountGenerate () {

        StringBuilder userAccount = new StringBuilder();

        for (int i = 0; i < UserConstant.USER_ACCOUNT_LENGTH; i ++) {
            String num = String.valueOf(RandomUtil.randomNumber());

            if (i == 0 && "0".equals(num)) {
                for (int j = 0; j < UserConstant.ACCOUNT_RETRY_NUM; j ++) {
                   num = String.valueOf(RandomUtil.randomNumber());

                   if (!"0".equals(num)) {
                       break;
                   }
                }
            }

            userAccount.append(num);
        }

        return userAccount.toString();
    }
}
