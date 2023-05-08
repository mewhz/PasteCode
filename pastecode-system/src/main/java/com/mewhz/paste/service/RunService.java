package com.mewhz.paste.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.CodeMapper;
import com.mewhz.paste.mapper.LogMapper;
import com.mewhz.paste.mapper.RunMapper;
import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.entity.Run;
import com.mewhz.paste.model.vo.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.util.List;

import static com.mewhz.paste.constant.LogConstant.DELETE_LIKE;
import static com.mewhz.paste.constant.LogConstant.RUN_CODE;
import static com.mewhz.paste.constant.RunConstant.*;

/**
 * @author mewhz
 */
@Service
public class RunService extends ServiceImpl<RunMapper, Run> {

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private RunMapper runMapper;

    @Resource
    private LogMapper logMapper;

    public RunResultVO receivedCode(CodeRunVO codeRunVO) {
        this.mkdirCode(codeRunVO);

        return this.runCode(codeRunVO);
    }

    @SneakyThrows
    private void mkdirCode (CodeRunVO codeRunVO) {
        String codeText = codeRunVO.getCodeText();
        String codeType = codeRunVO.getCodeType();
        String runInput = codeRunVO.getRunInput();
        String fileName = RUN_DIRECTORY;

        if (JAVA_TYPE.equals(codeType)) {
            fileName += JAVA_CODE_FILE;
        }
        else if (PYTHON_TYPE.equals(codeType)) {
            fileName += PYTHON_CODE_FILE;
        }
        else if (CPP_TYPE.equals(codeType)) {
            fileName += CPP_CODE_FILE;
        }

        FileWriter fileWriter = new FileWriter(fileName);

        fileWriter.write(codeText);

        fileWriter.close();


        fileName = RUN_DIRECTORY + codeType + INT_FILE_SUFFIX;

        fileWriter = new FileWriter(fileName);

        fileWriter.write(runInput);

        fileWriter.close();

    }

    private RunResultVO runCode(CodeRunVO codeRunVO) {

        String codeType = codeRunVO.getCodeType();
        String fileName = RUN_DIRECTORY + codeType + "Run.bat";

        RunResultVO runResultVO = new RunResultVO();

        RuntimeUtil.execForStr(CharsetUtil.CHARSET_UTF_8, fileName);

        fileName = RUN_DIRECTORY + codeType + OUT_FILE_SUFFIX;

        FileReader fileReader = new FileReader(fileName, CharsetUtil.CHARSET_GBK);

        runResultVO.setRunOutput(fileReader.readString());

        fileName = RUN_DIRECTORY + codeType + ERROR_FILE_SUFFIX;

        fileReader = new FileReader(fileName, CharsetUtil.CHARSET_GBK);

        runResultVO.setRunError(fileReader.readString());

        RuntimeUtil.execForStr(CharsetUtil.CHARSET_UTF_8, CLEAR_FILE_NAME);

        this.saveRun(runResultVO, codeRunVO);

        return runResultVO;
    }

    private void saveRun(RunResultVO runResultVO, CodeRunVO codeRunVO) {

        Code code = new Code();
        Run run = new Run();

        BeanUtil.copyProperties(codeRunVO, code);
        BeanUtil.copyProperties(runResultVO, run);
        BeanUtil.copyProperties(codeRunVO, run);

        code.setCodeStatus(1);

        this.codeMapper.insert(code);

        run.setCodeId(code.getCodeId());

        Log log = new Log();

        log.setLogInfo(codeRunVO.toString());
        log.setLogType(RUN_CODE);
        log.setLogIsSuccess(true);
        this.logMapper.insert(log);

        this.save(run);

    }
    
    public ResultPageVO<RunInfoVO> getPageList(RunSearchVO runSearchVO) {
        Integer count = runMapper.runTotal(runSearchVO);
        List<RunInfoVO> runs = runMapper.runPageList(runSearchVO, RUN_PAGE_NUM);
        return new ResultPageVO<>(runs, count);
    }
}
