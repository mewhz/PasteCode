package com.mewhz.paste.constant;

/**
 * @author mewhz
 */
public interface UserConstant {

    /**
     * 邮箱存在时提示的语句
     */
    String EMAIL_ERROR_MESSAGE = "邮箱已存在！";

    /**
     * 用户注册时账号长度
     */
    Integer USER_ACCOUNT_LENGTH = 8;

    /**
     * 注册时第一位为零时重试次数
     */

    Integer ACCOUNT_RETRY_NUM = 10;

    /**
     * 注册时账号循环次数
     */
    Integer ACCOUNT_RETRY_CYCLE = 1000;

    /**
     * 用户返回时每页的数量
     */
    Long USER_PAGE_NUM = 5L;
}
