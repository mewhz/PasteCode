package com.mewhz.paste.controller;

import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.Run;
import com.mewhz.paste.model.vo.*;
import com.mewhz.paste.service.RunService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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

    @GetMapping("/pageList")
    public ResultVO<ResultPageVO<RunInfoVO>> getPageList(RunSearchVO runSearchVO) {
        return ResultVO.ok(runService.getPageList(runSearchVO));
    }

    @GetMapping("/isRun/{codeId}")
    public ResultVO<CodeRunInfoVO> isRun(@PathVariable Integer codeId) {
        return ResultVO.ok(runService.isRun(codeId));
    }
}
