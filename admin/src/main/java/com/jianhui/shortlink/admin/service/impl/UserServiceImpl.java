package com.jianhui.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jianhui.shortlink.admin.commons.convention.exception.ClientException;
import com.jianhui.shortlink.admin.commons.convention.result.Result;
import com.jianhui.shortlink.admin.commons.convention.result.Results;
import com.jianhui.shortlink.admin.commons.enums.UserErrorCodeEnum;
import com.jianhui.shortlink.admin.dao.entity.UserDO;
import com.jianhui.shortlink.admin.dao.mapper.UserMapper;
import com.jianhui.shortlink.admin.remote.dto.resp.UserRespDTO;
import com.jianhui.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Override
    public Result<UserRespDTO> getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> wp = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
        UserDO user = this.getOne(wp);

        if(user == null){
            throw new ClientException(UserErrorCodeEnum.USER_NULL); //传的是errorCode
        }

        UserRespDTO userRespDTO = new UserRespDTO();
        BeanUtils.copyProperties(user,userRespDTO);

        return Results.success(userRespDTO);
    }
}
