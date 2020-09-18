package com.beyondsoft.aspect.notice;


import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.MailSendService;
import com.beyondsoft.service.SysNoticeService;
import com.beyondsoft.service.SysUserService;
import com.beyondsoft.utils.Const;
import com.beyondsoft.vo.SysNoticeVo;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SysNoticeAspect {

    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private MailSendService mailSendService;
    @Autowired
    private SysUserService sysUserService;

    @Pointcut("@annotation(com.beyondsoft.aspect.notice.SysNoticeAnno)")
    public void sysNoticeAspect(){

    }

    @Around("sysNoticeAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Object ret = null;
        Object[] args = pjp.getArgs();
        // Object ret = pjp.proceed();
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        method.getTypeParameters();
        // 获取方法上的注解
        SysNoticeAnno sysNoticeAnno = method.getAnnotation(SysNoticeAnno.class);
        String noticeType = sysNoticeAnno.noticeType();
        //根据不同的消息类型进行业务处理
        ret = pjp.proceed();
        return  ret;
    }


}
