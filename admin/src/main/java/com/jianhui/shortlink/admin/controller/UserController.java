package com.jianhui.shortlink.admin.controller;

import com.jianhui.shortlink.admin.commons.convention.result.Result;
import com.jianhui.shortlink.admin.remote.dto.resp.UserRespDTO;
import com.jianhui.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }
}
