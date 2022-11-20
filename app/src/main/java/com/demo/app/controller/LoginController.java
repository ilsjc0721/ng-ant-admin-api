package com.demo.app.controller;

import com.demo.app.service.login.LoginService;
import enums.ErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.sys.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;

@RestController
@Validated
@Api(value = "LoginController",tags = "登入管理")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "用戶登入")
    public Result login(@RequestBody @Validated UserDto user){
        return loginService.login(user);
    }

//    @PostMapping("/captcha")
//    @ApiOperation(value = "获取验证码")
//    public Result captcha(){
//        return loginService.captcha();
//    }
//
//    @GetMapping("/sessionTimeOut")
//    @ApiOperation(value = "模拟登陆超时")
//    public Result sessionTimeOut(){
//        return Result.failure(ErrorCodeEnum.SYS_ERR_TOKEN_EXPIRE);
//    }
}
