package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.LogCo;
import com.beyondsoft.entity.SysLog;
import com.beyondsoft.mapper.SysLogMapper;
import com.beyondsoft.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.utils.HttpResultEnum;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.SysLogVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-04-27
 */
@Service
@Transactional
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    /**
     * 添加日志
     * @param sysLog
     */
    @Override
    public Result addLog(SysLog sysLog) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        Integer ret = this.baseMapper.insert(sysLog);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
            r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 日志列表页
     * @param logCo
     * @return
     */
    @Override
    public PageData listPageData(LogCo logCo) {
        Page<SysLogVo> page = new Page<>(logCo.getPage(),logCo.getLimit());
        List<SysLogVo> logVoList = this.baseMapper.listPageData(logCo,page);
        page.setRecords(logVoList);
        return  new PageData(page.getTotal(),page.getRecords());
    }
}
