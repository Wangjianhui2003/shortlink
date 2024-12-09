package com.jianhui.shortlink.admin.commons.convention.exception;

import com.jianhui.shortlink.admin.commons.convention.errorcode.IErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;
//不同子类的区别就是默认的errorCode不同(BaseErrorCode
@Getter
public abstract class AbstractException extends RuntimeException {

    public final String errorCode;

    public final String errorMessage;

    //抛异常时有message就设为message,没有就用errorCode的message
    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.message());
    }
}
