package com.beyondsoft.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.co.GrantBoundsCo;
import com.beyondsoft.entity.GrantBounds;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.GrantBoundsVo;

import java.util.List;

/**
 * <p>
 * 奖励管理 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2020-09-03
 */
public interface GrantBoundsMapper extends BaseMapper<GrantBounds> {

    List<GrantBoundsVo> getPageDataList(GrantBoundsCo grantBoundsCo, Page<GrantBoundsVo> page);
}
