package com.mewhz.paste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.paste.model.entity.Run;
import com.mewhz.paste.model.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mewhz
 */
@Repository
public interface RunMapper extends BaseMapper<Run> {

    Integer runTotal(RunSearchVO runSearchVO);

    List<RunInfoVO> runPageList(@Param("runSearchVO") RunSearchVO runSearchVO, @Param("size") Long size);


}
