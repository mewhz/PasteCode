package com.mewhz.paste.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.CodeInfoMapper;
import com.mewhz.paste.model.dto.CodeInfoResponse;
import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.CodeInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mewhz
 */
@Service
public class CodeInfoService extends ServiceImpl<CodeInfoMapper, CodeInfo> {

    public List<CodeInfoResponse> selectAll() {
        return baseMapper.selectAll();
    }

    public CodeInfoResponse selectByCodeId(Integer codeId) {
        return baseMapper.selectByCodeId(codeId);
    }
}
