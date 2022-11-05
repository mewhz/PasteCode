package com.mewhz.paste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.CodeMapper;
import com.mewhz.paste.model.entity.Code;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mewhz
 */
@Service
public class CodeService extends ServiceImpl<CodeMapper, Code> {

    public List<Code> findByCodeTitle(String codeTitle) {
        return this.list(new QueryWrapper<Code> ().like("code_title", codeTitle));
    }

    public List<Code> findByCodeTitlePage(Integer current, String codeTitle) {
        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code_title", codeTitle);

        long total = this.count(queryWrapper);

        Page<Code> page = new Page<>(current, 3, total);

        page = this.page(page, queryWrapper);

        return new ArrayList<>(page.getRecords());
    }

    public Long getCodeTitleTotal(String codeTitle) {

        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code_title", codeTitle);

        return this.count(queryWrapper);
    }
}
