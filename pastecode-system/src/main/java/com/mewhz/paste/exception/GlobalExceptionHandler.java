package com.mewhz.paste.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.mewhz.paste.common.BaseResponse;
import com.mewhz.paste.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author mewhz
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<?> handlerNotLoginException(NotLoginException e) {
        return ResultUtils.error(e.getMessage());
    }
}
