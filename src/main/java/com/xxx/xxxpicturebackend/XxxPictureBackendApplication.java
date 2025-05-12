package com.xxx.xxxpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.xxx.xxxpicturebackend.mapper")
@SpringBootApplication
public class XxxPictureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxxPictureBackendApplication.class, args);
    }

}
