package com.mewhz.paste.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.LogMapper;
import com.mewhz.paste.mapper.StatusMapper;
import com.mewhz.paste.model.entity.Code;
import com.mewhz.paste.model.entity.Log;
import com.mewhz.paste.model.entity.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.mewhz.paste.constant.LogConstant.*;

/**
 * @author mewhz
 */
@Service
public class StatusService extends ServiceImpl<StatusMapper, Status> {

    @Resource
    private LogMapper logMapper;

    public boolean updateStatus(Status status) {

        Status statusExist = this.getOne(new LambdaQueryWrapper<Status>()
                .eq(Status::getCodeId, status.getCodeId())
                .eq(Status::getUserId, status.getUserId())
                .eq(Status::getStatusType, status.getStatusType()));

        Log log = new Log();

        if (statusExist == null) {

            log.setLogInfo(status.toString());

            if (status.getStatusType() == 1) {
                log.setLogType(INSERT_LIKE);
            }
            else {
                log.setLogType(INSERT_COLLECT);
            }

            log.setLogIsSuccess(true);

            this.logMapper.insert(log);

            return this.save(status);
        }
        else {
            status.setStatusId(statusExist.getStatusId());


            log.setLogInfo(status.toString());

            if (status.getStatusType() == 1) {
                log.setLogType(DELETE_LIKE);
            }
            else {
                log.setLogType(DELETE_COLLECT);
            }

            log.setLogIsSuccess(true);

            this.logMapper.insert(log);

            return this.removeById(status);
        }
    }

    public Long total(Integer statusType, Integer codeId) {

        return this.count(new LambdaQueryWrapper<Status>()
                .eq(Status::getCodeId, codeId)
                .eq(Status::getStatusType, statusType));
    }

    public Boolean userIsStatus(Integer statusType, Integer userId, Integer codeId) {
        Status statusExist = this.getOne(new LambdaQueryWrapper<Status>()
                .eq(Status::getCodeId, codeId)
                .eq(Status::getUserId, userId)
                .eq(Status::getStatusType, statusType));

        return statusExist != null;
    }


}
