package com.jianhui.shortlink.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject,"createTime",LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject,"updateTime",LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject,"delFlag",()->0,Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject,"updateTime",LocalDateTime::now, LocalDateTime.class);
    }
}
