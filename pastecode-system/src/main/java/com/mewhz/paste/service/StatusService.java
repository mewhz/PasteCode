package com.mewhz.paste.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mewhz.paste.mapper.StatusMapper;
import com.mewhz.paste.model.entity.Status;
import org.springframework.stereotype.Service;

/**
 * @author mewhz
 */
@Service
public class StatusService extends ServiceImpl<StatusMapper, Status> {


    public boolean updateStatus(Status status) {

        Status statusExist = this.getOne(new LambdaQueryWrapper<Status>()
                .eq(Status::getCodeId, status.getCodeId())
                .eq(Status::getUserId, status.getUserId())
                .eq(Status::getStatusType, status.getStatusType()));

        if (statusExist == null) {
            return this.save(status);
        }
        else {
            status.setStatusId(statusExist.getStatusId());

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
