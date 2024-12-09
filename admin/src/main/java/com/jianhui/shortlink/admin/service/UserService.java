package com.jianhui.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jianhui.shortlink.admin.commons.convention.result.Result;
import com.jianhui.shortlink.admin.dao.entity.UserDO;
import com.jianhui.shortlink.admin.remote.dto.req.UserRegisterReqDTO;
import com.jianhui.shortlink.admin.remote.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDO> {
    Result<UserRespDTO> getUserByUsername(String username);

    Boolean hasUsername(String username);

    void register(UserRegisterReqDTO userReqDTO);
}
