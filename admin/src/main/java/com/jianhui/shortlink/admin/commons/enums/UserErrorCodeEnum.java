package com.jianhui.shortlink.admin.commons.enums;

import com.jianhui.shortlink.admin.commons.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {
    USER_NULL("B000200","用户不存在");

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
