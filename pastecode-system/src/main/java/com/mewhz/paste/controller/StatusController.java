package com.mewhz.paste.controller;

import com.mewhz.paste.model.entity.Status;
import com.mewhz.paste.model.vo.ResultVO;
import com.mewhz.paste.service.StatusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/status")
public class StatusController {

    @Resource
    private StatusService statusService;

    @PostMapping("/update")
    public ResultVO<Boolean> update(@RequestBody Status status) {
        return ResultVO.ok(statusService.updateStatus(status));
    }

    @GetMapping("/total/{statusType}/{codeId}")
    public ResultVO<Long> likeTotal(@PathVariable Integer statusType, @PathVariable Integer codeId) {
        return ResultVO.ok(statusService.total(statusType, codeId));
    }

    @GetMapping("/userIsStatus/{statusType}/{userId}/{codeId}")
    public ResultVO<Boolean> userIsStatus(@PathVariable Integer statusType, @PathVariable Integer userId, @PathVariable Integer codeId) {
        return ResultVO.ok(statusService.userIsStatus(statusType, userId, codeId));
    }

}
