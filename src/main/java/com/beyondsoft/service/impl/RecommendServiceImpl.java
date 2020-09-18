package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.RecommendCo;
import com.beyondsoft.entity.Recommend;
import com.beyondsoft.mapper.RecommendMapper;
import com.beyondsoft.service.RecommendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.vo.RecommendVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 推荐记录 服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-09-02
 */
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {

    @Autowired
    private RecommendMapper recommendMapper;
    /**
     * 加载页面数据
     * @param recommendCo
     * @return
     */
    @Override
    public PageData getRecommendPageList(RecommendCo recommendCo) {
        Page<RecommendVo> page = new Page(recommendCo.getPage(),recommendCo.getLimit());
        page.setRecords(recommendMapper.getRecommendList(recommendCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }
}
