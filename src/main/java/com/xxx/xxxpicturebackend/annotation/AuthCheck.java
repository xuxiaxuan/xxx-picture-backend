package com.xxx.xxxpicturebackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: // 类说明，在创建类时要填写
 * @ClassName: AuthCheck    // 类名，会自动填充
 * @Author: 许夏轩          // 创建者
 * @Date: 06/05/2025 10:35 PM   // 时间
 * @Version: 1.0     // 版本
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须有某个角色
     */
    String mustRole() default "";
}

