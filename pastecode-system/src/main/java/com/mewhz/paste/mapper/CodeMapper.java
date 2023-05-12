package com.mewhz.paste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mewhz
 */
@Repository
public interface CodeMapper extends BaseMapper<Code> {


    CodeInfoVO findByCodeId (Integer codeId);

    List<CodeInfoVO> userShareCode(Integer userId);

    Integer codeTotal(CodeSearchVO codeSearchVO);

    List<CodeInfoVO> codePageList(@Param("codeSearchVO") CodeSearchVO codeSearchVO, @Param("size") Long size);

    List<CodeStatusInfo> codeStatusList(@Param("codeAuthorIds") List<Integer> codeAuthorIds, @Param("userId") Integer userId);

    List<CodeStatusInfo> userCollectCode(@Param("userId") Integer userId);

    List<CodePieInfo> codePieInfo();

    List<CodeHeatmapInfo> codeDate(Long year);

    List<CodeHeatmapInfo> userCodeDate(@Param("year") Long year, @Param("userId") Integer userId);
}
