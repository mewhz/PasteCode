package com.mewhz.paste.controller;

import com.mewhz.paste.service.LogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;
}
