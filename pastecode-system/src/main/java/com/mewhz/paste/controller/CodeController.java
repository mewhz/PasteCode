package com.mewhz.paste.controller;


import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.Run;
import com.mewhz.paste.model.vo.*;
import com.mewhz.paste.service.CodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    @Resource
    private CodeService codeService;


    @GetMapping("/")
    public ResultVO<List<Code>> findAll(){
        return ResultVO.ok();
    }

    @PostMapping("/")
    public ResultVO<CodeInfoVO> saveCode(@RequestBody CodeSubmitVO codeSubmitVO){
        return ResultVO.ok(codeService.saveCode(codeSubmitVO));
    }

    @GetMapping("/id/{codeId}")
    public ResultVO<CodeInfoVO> findByCodeId(@PathVariable("codeId") Integer codeId){
        return ResultVO.ok(codeService.findByCodeId(codeId));
    }

    @GetMapping("/title/{codeTitle}")
    public ResultVO<List<CodeInfoVO>> findByCodeTitle(@PathVariable String codeTitle){
        return ResultVO.ok(codeService.findByCodeTitle(codeTitle));
    }

    @GetMapping("/page/{codeTitle}/{current}")
    public ResultVO<List<CodeInfoVO>> findByCodeTitlePage(@PathVariable Integer current, @PathVariable String codeTitle) {
        return ResultVO.ok(codeService.findByCodeTitlePage(current, codeTitle));
    }

    @GetMapping("/page/total/{codeTitle}")
    public ResultVO<Long> getCodeTitleTotal(@PathVariable String codeTitle) {
        return ResultVO.ok(codeService.getCodeTitleTotal(codeTitle));
    }

    @GetMapping("/share/{userId}")
    public ResultVO<List<CodeInfoVO>> getUserShareCode(@PathVariable Integer userId) {
        return ResultVO.ok(codeService.userShareCode(userId));
    }

    @GetMapping("/pageList")
    public ResultVO<ResultPageVO<CodeInfoVO>> getPageList(CodeSearchVO codeSearchVO) {
        return ResultVO.ok(codeService.getPageList(codeSearchVO));
    }

    @PostMapping("/delete")
    public ResultVO<Boolean> delete(@RequestBody Code code) {
        return ResultVO.ok(codeService.deleteCode(code));
    }

    @PostMapping("/update")
    public ResultVO<Boolean> update(@RequestBody Code code) {
        return ResultVO.ok(codeService.updateCode(code));
    }

    @GetMapping("/download/{codeId}")
    public void download(HttpServletResponse response, @PathVariable Integer codeId) throws IOException {
        codeService.download(response, codeId);
    }

    @GetMapping("/getStatusList/{userId}")
    public ResultVO<List<CodeStatusInfo>> getStatusList(@PathVariable Integer userId) {
       return ResultVO.ok(codeService.getStatusList(userId));
    }

    @GetMapping("/getUserCollectList/{userId}")
    public ResultVO<List<CodeStatusInfo>> getUserCollectList(@PathVariable Integer userId) {
        return ResultVO.ok(codeService.userCollectCode(userId));
    }

    @GetMapping("/getUserRunCodeList/{userId}")
    public ResultVO<List<Run>> getUserRunCodeList(@PathVariable Integer userId) {
        return ResultVO.ok(codeService.getUserRunCodeList(userId));
    }

    @GetMapping("/getShareTotal")
    public ResultVO<Long> getShareTotal() {
        return ResultVO.ok(codeService.getShareTotal());
    }

    @GetMapping("/getRunTotal")
    public ResultVO<Long> getRunTotal() {
        return ResultVO.ok(codeService.getRunTotal());
    }

    @GetMapping("/getCodePieInfo")
    public ResultVO<List<CodePieInfo>> getCodePieInfo() {
        return ResultVO.ok(codeService.getCodePieInfo());
    }

    @GetMapping("/getCodeDate/{year}")
    public ResultVO<List<CodeHeatmapInfo>> getCodeDate(@PathVariable Long year) {
        return ResultVO.ok(codeService.getCodeDate(year));
    }

    @GetMapping("/getUserCodeDate/{year}/{userId}")
    public ResultVO<List<CodeHeatmapInfo>> getUserCodeDate(@PathVariable Long year, @PathVariable Integer userId) {
        return ResultVO.ok(codeService.getUserCodeDate(year, userId));
    }

}
