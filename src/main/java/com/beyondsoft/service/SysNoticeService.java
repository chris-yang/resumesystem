package com.beyondsoft.service;

import com.beyondsoft.entity.SysNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.vo.SysNoticeVo;

import java.util.List;

/**
 * <p>
 * 系统公告 服务类
 * </p>
 *
 * @author kun
 * @since 2020-04-28
 */
public interface SysNoticeService extends IService<SysNotice> {
    /**保存通知类型**/
    boolean saveNotice(SysNoticeVo sysNoticeVo);
    /**根据id获取通知内容**/
    List<SysNoticeVo> getNoticeVoByUserId(Integer userId);
}
