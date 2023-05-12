package com.mewhz.paste.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.constant.LogConstant;
import com.mewhz.paste.mapper.LogMapper;
import com.mewhz.paste.model.dto.LogTypeInfoTotalDTO;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.vo.LogPieInfo;
import com.mewhz.paste.model.vo.LogSearchVO;
import com.mewhz.paste.model.vo.ResultPageVO;
import com.mewhz.paste.model.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mewhz.paste.constant.LogConstant.*;
/**
 * @author mewhz
 */
@Service
public class LogService extends ServiceImpl<LogMapper, Log> {

    @Resource
    private LogMapper logMapper;

    public ResultPageVO<Log> getPageList(LogSearchVO logSearchVO) {
        Integer count = logMapper.logTotal(logSearchVO);
        List<Log> logs = logMapper.logPageList(logSearchVO, LOG_PAGE_NUM);
        return new ResultPageVO<>(logs, count);
    }

    public List<String> getLogType() {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("distinct log_type");

        return logMapper.selectList(queryWrapper)
                .stream()
                .map(Log::getLogType)
                .collect(Collectors.toList());
    }

    public List<LogPieInfo> logTotalList() {
        List<LogTypeInfoTotalDTO> list = logMapper.logTotalList();

        LogPieInfo likeAndCollect = new LogPieInfo(0L, LIKE_AND_COLLECT);
        LogPieInfo managementLog = new LogPieInfo(0L, MANAGEMENT_LOG);
        LogPieInfo loginRegister = new LogPieInfo(0L, LOGIN_REGISTER);
        LogPieInfo runPaste = new LogPieInfo(0L, RUN_PASTE);

        for (LogTypeInfoTotalDTO dto : list) {
            String logType = dto.getLogType();
            Long total = dto.getLogTypeTotal();
            if (logType.equals(INSERT_LIKE)
                    || logType.equals(DELETE_LIKE)
                    || logType.equals(INSERT_COLLECT)
                    || logType.equals(DELETE_COLLECT)) {
                likeAndCollect.setLogTypeTotal(likeAndCollect.getLogTypeTotal() + total);
            }
            else if (logType.equals(UPDATE_USER)
                    || logType.equals(DELETE_USER)
                    || logType.equals(UPDATE_CODE)
                    || logType.equals(DELETE_CODE)) {
                managementLog.setLogTypeTotal(managementLog.getLogTypeTotal() + total);
            }
            else if (logType.equals(LOGIN)
                    || logType.equals(REGISTER)) {
                loginRegister.setLogTypeTotal(loginRegister.getLogTypeTotal() + total);
            }
            else if (logType.equals(INSERT_CODE)
                    || logType.equals(RUN_CODE)) {
                runPaste.setLogTypeTotal(runPaste.getLogTypeTotal() + total);
            }
        }

        List<LogPieInfo> result = new ArrayList<>();

        result.add(likeAndCollect);
        result.add(managementLog);
        result.add(loginRegister);
        result.add(runPaste);

        return result;
    }
}
