package com.mewhz.paste.controller;

import com.mewhz.paste.model.dto.LogTypeInfoTotalDTO;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.vo.LogPieInfo;
import com.mewhz.paste.model.vo.LogSearchVO;
import com.mewhz.paste.model.vo.ResultPageVO;
import com.mewhz.paste.model.vo.ResultVO;
import com.mewhz.paste.service.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;

    @GetMapping("/pageList")
    public ResultVO<ResultPageVO<Log>> getPageList(LogSearchVO logSearchVO) {
        return ResultVO.ok(logService.getPageList(logSearchVO));
    }

    @GetMapping("/getLogType")
    public ResultVO<List<String>> getLogType() {
        return ResultVO.ok(logService.getLogType());
    }

    @GetMapping("/getLogPieInfo")
    public ResultVO<List<LogPieInfo>> getLogPieInfo() {
        return ResultVO.ok(logService.logTotalList());
    }
}
