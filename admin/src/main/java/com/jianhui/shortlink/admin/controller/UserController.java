package com.jianhui.shortlink.admin.controller;

import com.jianhui.shortlink.admin.commons.convention.result.Result;
import com.jianhui.shortlink.admin.commons.convention.result.Results;
import com.jianhui.shortlink.admin.remote.dto.req.UserRegisterReqDTO;
import com.jianhui.shortlink.admin.remote.dto.resp.UserRespDTO;
import com.jianhui.shortlink.admin.service.UserService;
import com.jianhui.shortlink.admin.service.impl.UserServiceImpl;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用过用户名获取用户信息
     * @param username
     * @return
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@PathParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO userReqDTO){
        userService.register(userReqDTO);
        return Results.success();
    }
}
