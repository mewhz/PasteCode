package com.mewhz.paste.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.LogMapper;
import com.mewhz.paste.model.entity.Log;
import org.springframework.stereotype.Service;

/**
 * @author mewhz
 */
@Service
public class LogService extends ServiceImpl<LogMapper, Log> {
}
