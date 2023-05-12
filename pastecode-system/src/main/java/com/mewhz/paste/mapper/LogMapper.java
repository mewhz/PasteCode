package com.mewhz.paste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.paste.model.dto.LogTypeInfoTotalDTO;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.vo.LogSearchVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mewhz
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {

    Integer logTotal(LogSearchVO logSearchVO);

    List<Log> logPageList(@Param("logSearchVO") LogSearchVO logSearchVO, @Param("size") Long size);

    List<LogTypeInfoTotalDTO> logTotalList();

}
