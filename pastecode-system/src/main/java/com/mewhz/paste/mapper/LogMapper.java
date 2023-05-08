package com.mewhz.paste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.paste.model.entity.Log;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author mewhz
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {
}
