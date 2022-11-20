package com.mewhz.paste.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.paste.model.dto.UserSearchVO;
import com.mewhz.paste.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author mewhz
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    Integer userTotal(UserSearchVO userSearchVO);

    List<User> userList(@Param("userSearchVO") UserSearchVO userSearchVO, @Param("size") Long size);
}
