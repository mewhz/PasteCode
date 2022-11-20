package com.mewhz.paste.handler;

import com.mewhz.paste.exception.BizException;
import com.mewhz.paste.model.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.mewhz.paste.enums.StatusCodeEnum.*;

/**
 * @author mewhz
 */
@RestControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(value = BizException.class)
    public ResultVO<?> errorHandler(BizException e) {
        return ResultVO.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResultVO<?> errorHandler(Exception e) {
        e.printStackTrace();
        return ResultVO.fail(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMessage());
    }
}
