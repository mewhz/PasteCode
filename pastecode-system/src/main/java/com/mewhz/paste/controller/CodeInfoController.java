package com.mewhz.paste.controller;

import com.mewhz.paste.common.BaseResponse;
import com.mewhz.paste.common.ResultUtils;
import com.mewhz.paste.model.dto.CodeInfoResponse;
import com.mewhz.paste.service.CodeInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/codeInfo")
public class CodeInfoController {

    @Resource
    private CodeInfoService codeInfoService;

    @GetMapping("/")
    public BaseResponse<List<CodeInfoResponse>> selectAll() {
        return ResultUtils.success(codeInfoService.selectAll());
    }

    @GetMapping("codeId/{codeId}")
    public BaseResponse<CodeInfoResponse> selectByCodeId(@PathVariable Integer codeId) {
        return ResultUtils.success(codeInfoService.selectByCodeId(codeId));
    }
}
