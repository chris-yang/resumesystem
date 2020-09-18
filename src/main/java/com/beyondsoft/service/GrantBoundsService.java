package com.beyondsoft.service;

import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.GrantBoundsCo;
import com.beyondsoft.entity.GrantBounds;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 奖励管理 服务类
 * </p>
 *
 * @author kun
 * @since 2020-09-03
 */
public interface GrantBoundsService extends IService<GrantBounds> {
    /**加载内推奖励列表**/
    PageData getPageDataList(GrantBoundsCo grantBoundsCo);
}
