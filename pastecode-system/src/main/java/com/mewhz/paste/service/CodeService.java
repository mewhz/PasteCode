package com.mewhz.paste.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.cglib.CglibUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.CodeMapper;
import com.mewhz.paste.mapper.LogMapper;
import com.mewhz.paste.mapper.RunMapper;
import com.mewhz.paste.mapper.UserMapper;
import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.entity.Run;
import com.mewhz.paste.model.entity.User;
import com.mewhz.paste.model.vo.*;
import com.mewhz.paste.utils.CodeUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mewhz.paste.constant.CodeConstant.CODE_PAGE_NUM;
import static com.mewhz.paste.constant.LogConstant.*;

/**
 * @author mewhz
 */
@Service
public class CodeService extends ServiceImpl<CodeMapper, Code> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CodeMapper codeMapper;

    @Resource
    private RunMapper runMapper;

    @Resource
    private LogMapper logMapper;

    public CodeInfoVO findByCodeId(Integer codeId) {
        return codeMapper.findByCodeId(codeId);
    }

    public CodeInfoVO saveCode(CodeSubmitVO codeSubmitVO) {
        Code code = new Code();
        CodeInfoVO codeInfoVO = new CodeInfoVO();
        System.out.println("codeSubmitVO = " + codeSubmitVO);
        BeanUtil.copyProperties(codeSubmitVO, code);

        codeMapper.insert(code);

        System.out.println("code = " + code);

        if (code.getCodeAuthorId() != null) {
            User user = userMapper.selectById(code.getCodeAuthorId());
            System.out.println("user = " + user);
            user.setUserCodeNum(user.getUserCodeNum() + 1);

            userMapper.updateById(user);
        }

        BeanUtil.copyProperties(code, codeInfoVO);

        Log log = new Log();

        log.setLogInfo(JSONUtil.toJsonStr(codeSubmitVO));
        log.setLogType(INSERT_CODE);
        log.setLogIsSuccess(true);

        this.logMapper.insert(log);


        return codeInfoVO;
    }

    public List<CodeInfoVO> findByCodeTitle(String codeTitle) {
        List<Code> codeList = this.list(new LambdaQueryWrapper<Code>()
                .like(Code::getCodeTitle, codeTitle));
        return CglibUtil.copyList(codeList, CodeInfoVO::new);
    }

    public List<CodeInfoVO> findByCodeTitlePage(Integer current, String codeTitle) {
        LambdaQueryWrapper<Code> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Code::getCodeTitle, codeTitle);
        long total = this.count(queryWrapper);
        Page<Code> page = new Page<>(current, CODE_PAGE_NUM, total);
        page = this.page(page, queryWrapper);
        List<Code> codeList = new ArrayList<>(page.getRecords());
        return CglibUtil.copyList(codeList, CodeInfoVO::new);
    }

    public Long getCodeTitleTotal(String codeTitle) {

        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code_title", codeTitle);

        return this.count(queryWrapper);
    }

    public List<CodeInfoVO> userShareCode(Integer userId) {
        return codeMapper.userShareCode(userId);

    }

    public ResultPageVO<CodeInfoVO> getPageList(CodeSearchVO codeSearchVO) {
        Integer count = codeMapper.codeTotal(codeSearchVO);
        List<CodeInfoVO> codes = codeMapper.codePageList(codeSearchVO, CODE_PAGE_NUM);
        return new ResultPageVO<>(codes, count);
    }

    public void download(HttpServletResponse response, Integer codeId) throws IOException {

        Code code = codeMapper.selectById(codeId);

        String fileName = code.getCodeTitle() + "-" + System.currentTimeMillis() + CodeUtils.getCodeFileSuffix(code.getCodeType());

        response.setCharacterEncoding("UTF-8");

        // 设置响应类型为二进制
        response.setContentType("application/octet-stream");
        // 设置响应头
        // 文件的处理方式, attachment 表示附件，filename 表示文件的名称
        // 防止中文乱码
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition
                        .attachment().
                        filename(fileName, StandardCharsets.UTF_8)
                        .build()
                        .toString());

        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");

        byte[] bytes = code.getCodeText().getBytes();

        response.getOutputStream().write(bytes);
    }

    public List<CodeStatusInfo> getStatusList(Integer userId) {

        List<Integer> codeAuthorIds = codeMapper
                .userShareCode(userId)
                .stream()
                .map(CodeInfoVO::getCodeId)
                .collect(Collectors.toList());


        if (codeAuthorIds.size() == 0) {
            return null;
        }

        return codeMapper.codeStatusList(codeAuthorIds, userId);
    }

    public List<CodeStatusInfo> userCollectCode(Integer userId){
        return codeMapper.userCollectCode(userId);
    }

    public List<Run> getUserRunCodeList(Integer userId) {
        List<Integer> codeIds = this.list(new LambdaQueryWrapper<Code>()
                .eq(Code::getCodeAuthorId, userId))
                .stream()
                .map(Code::getCodeId)
                .collect(Collectors.toList());

        if (codeIds.size() == 0) {
            return null;
        }

        return runMapper.selectList(new LambdaQueryWrapper<Run>().in(Run::getCodeId, codeIds));
    }

    public Boolean deleteCode(Code code){

        Log log = new Log();

        log.setLogInfo(JSONUtil.toJsonStr(code));
        log.setLogType(DELETE_CODE);
        log.setLogIsSuccess(true);

        this.logMapper.insert(log);

        return this.removeById(code);
    }


    public Boolean updateCode(Code code){

        Log log = new Log();

        log.setLogInfo(JSONUtil.toJsonStr(code));
        log.setLogType(UPDATE_CODE);
        log.setLogIsSuccess(true);

        this.logMapper.insert(log);

        return this.updateById(code);
    }

    public Long getShareTotal() {
        return this.count(new LambdaQueryWrapper<Code>()
                        .eq(Code::getCodeStatus, 0));
    }

    public Long getRunTotal() {
        return this.count(new LambdaQueryWrapper<Code>()
                        .eq(Code::getCodeStatus, 1));
    }

    public List<CodePieInfo> getCodePieInfo() {
        return codeMapper.codePieInfo();
    }

    public List<CodeHeatmapInfo> getCodeDate(Long year) {
        return codeMapper.codeDate(year);
    }

    public List<CodeHeatmapInfo> getUserCodeDate(Long year, Integer userId) {
        return codeMapper.userCodeDate(year, userId);
    }
}
