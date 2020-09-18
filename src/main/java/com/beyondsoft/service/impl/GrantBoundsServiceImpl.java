package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.GrantBoundsCo;
import com.beyondsoft.entity.GrantBounds;
import com.beyondsoft.mapper.GrantBoundsMapper;
import com.beyondsoft.service.GrantBoundsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.vo.GrantBoundsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 奖励管理 服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-09-03
 */
@Service
public class GrantBoundsServiceImpl extends ServiceImpl<GrantBoundsMapper, GrantBounds> implements GrantBoundsService {

    @Autowired
    private GrantBoundsMapper grantBoundsMapper;
    @Override
    public PageData getPageDataList(GrantBoundsCo grantBoundsCo) {
        Page<GrantBoundsVo> page = new Page(grantBoundsCo.getPage(),grantBoundsCo.getLimit());
        page.setRecords(grantBoundsMapper.getPageDataList(grantBoundsCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }
}
