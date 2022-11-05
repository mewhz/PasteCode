package com.mewhz.paste.controller;


import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mewhz
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @GetMapping("/")
    public List<Code> findAll(){
        return codeService.list();
    }

    @PostMapping("/")
    public Map<String, Object> save(@RequestBody Code code){
        Map<String, Object> result = new HashMap<>();
        codeService.save(code);

        System.out.println(code);

        result.put("status", 200);
        result.put("msg", "success");
        result.put("codeId", code.getCodeId());
        return result;
    }

    @GetMapping("/id/{id}")
    public Code findById(@PathVariable("id") Integer id){
        return codeService.getById(id);
    }

    @GetMapping("/title/{codeTitle}")
    public List<Code> findByCodeTitle(@PathVariable String codeTitle){
        return codeService.findByCodeTitle(codeTitle);
    }

    @GetMapping("/page/{codeTitle}/{current}")
    public List<Code> findByCodeTitlePage(@PathVariable Integer current, @PathVariable String codeTitle) {
        return codeService.findByCodeTitlePage(current, codeTitle);
    }

    @GetMapping("/page/total/{codeTitle}")
    public Long getCodeTitleTotal(@PathVariable String codeTitle) {
        return codeService.getCodeTitleTotal(codeTitle);
    }

}
