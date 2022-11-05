package com.mewhz.paste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.paste.model.dto.CodeInfoResponse;
import com.mewhz.paste.model.entity.CodeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author mewhz
 */
public interface CodeInfoMapper extends BaseMapper<CodeInfo> {

    /**
     * 查询所有 Code 中所有信息
     * @return 返回所有 Code 和 CodeInfo 中所有信息
     */
    @Select("SELECT paste_code.code.*, paste_code.code_info.* " +
            "FROM paste_code.CODE, paste_code.code_info\n" +
            "WHERE code.code_id = code_info.code_id")
    List<CodeInfoResponse> selectAll();

    /**
     * 根据代码 ID 查询代码的所有信息
     * @param codeId 被查询的代码 ID
     * @return 这个代码的所有信息
     */
    @Select("SELECT paste_code.code.*, paste_code.code_info.* " +
            "FROM paste_code.CODE, paste_code.code_info\n" +
            "WHERE code.code_id = code_info.code_id AND code.code_id = #{codeId}")
    CodeInfoResponse selectByCodeId(@Param("codeId") Integer codeId);
}