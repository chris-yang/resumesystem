package com.beyondsoft.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.co.LogCo;
import com.beyondsoft.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.SysLogVo;

import java.util.List;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2020-04-27
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    List<SysLogVo> listPageData(LogCo logCo, Page<SysLogVo> page);
}
