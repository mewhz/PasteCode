package com.mewhz.paste.controller;

import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.Run;
import com.mewhz.paste.model.vo.CodeRunVO;
import com.mewhz.paste.model.vo.ResultVO;
import com.mewhz.paste.model.vo.RunResultVO;
import com.mewhz.paste.service.RunService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/run")
public class RunController {

    @Resource
    private RunService runService;

    @PostMapping("/")
    public ResultVO<RunResultVO> receivedCode(@RequestBody CodeRunVO codeRunVO) {
        return ResultVO.ok(runService.receivedCode(codeRunVO));
    }
}
