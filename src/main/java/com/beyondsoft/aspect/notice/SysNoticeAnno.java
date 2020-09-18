package com.beyondsoft.aspect.notice;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysNoticeAnno {
    String noticeType() default ""; //消息类型
}
