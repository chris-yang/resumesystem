package com.beyondsoft.service;

import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.LogCo;
import com.beyondsoft.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.utils.Result;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author kun
 * @since 2020-04-27
 */
public interface SysLogService extends IService<SysLog> {
    /** 记录日志 */
    Result addLog(SysLog sysLog);
    /** 记录列表 */
    PageData listPageData(LogCo logCo);
}
