package com.accounting.mapper;

import com.accounting.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据openid查找用户
     */
    User findByOpenid(@Param("openid") String openid);

    /**
     * 更新用户信息
     */
    int updateUserInfo(@Param("user") User user);
}