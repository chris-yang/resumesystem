package com.beyondsoft.controller;


import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.LogCo;
import com.beyondsoft.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author kun
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/sys-log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;
    @GetMapping("/pagedata")
    public PageData listPageData(LogCo logCo) {
        return  sysLogService.listPageData(logCo);
    }

}
