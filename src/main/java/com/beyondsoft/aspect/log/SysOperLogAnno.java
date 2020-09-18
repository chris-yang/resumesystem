package com.beyondsoft.aspect.log;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysOperLogAnno {
    /** 日志类型 */
    int logType() default 100;      // 100-登陆  200-系统
    String module() default "";     // 模块名， 后期可以改成模块编号，与function对应
    String methods() default "";    // 方法的中文描述
}
