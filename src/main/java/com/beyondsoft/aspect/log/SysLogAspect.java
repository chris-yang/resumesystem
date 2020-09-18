package com.beyondsoft.aspect.log;

import com.beyondsoft.entity.SysLog;
import com.beyondsoft.entity.SysUser;
import com.beyondsoft.service.SysLogService;
import com.beyondsoft.utils.IPUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

//
@Component
@Aspect
public class SysLogAspect {
//    private static final Logger =Logger.getLogger(SysLogAspect.class)


    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.beyondsoft.aspect.log.SysOperLogAnno)")
    private void controllerAspect(){

    }

    @Around("controllerAspect()")
    public  Object around(ProceedingJoinPoint pjp) throws Throwable {
        SysLog sysLog = null;
        Object ret = null;
        Object[] orgs = pjp.getArgs();
        if (orgs != null && orgs.length > 0) {
            for (int i = 0; i < orgs.length; i++) {
                if (orgs[i] instanceof SysOperLogAnno) {
                    sysLog = (SysLog)orgs[i];
                }
            }
        }
        if (sysLog == null) {
            sysLog = new SysLog();
        }
        // Object ret = pjp.proceed();
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        // 获取方法上的注解
        SysOperLogAnno sysLogAnno = method.getAnnotation(SysOperLogAnno.class);
        int operType = sysLogAnno.logType();
        String module = sysLogAnno.module();
        String methods = sysLogAnno.methods();
        sysLog.setLogType(operType);
        sysLog.setModule(module);
        sysLog.setMethod(methods);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = null;
        String comment = "正常执行";
        if (operType == 100) {
            username = request.getParameter("email");
            comment = "请求用户: " + username;
        } else {
            SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
            username =  user.getRealName();
        }
        String url = request.getServletPath();
        String ip = IPUtil.getClientIp(request);
        sysLog.setUserName(username);
        sysLog.setIp(ip);
        sysLog.setUrl(url);
        try {
            ret = pjp.proceed();
            sysLog.setComment(comment);
        } catch (Exception e) {
            //logger.error("syslog aspect fail");
            comment = "异常执行";
            sysLog.setComment(comment);
        } finally {
            sysLog.setCreateTime(new Date());
            sysLogService.addLog(sysLog);
        }
        return ret;
    }
}
