package com.beyondsoft.service;

import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.RecommendCo;
import com.beyondsoft.entity.Recommend;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 推荐记录 服务类
 * </p>
 *
 * @author kun
 * @since 2020-09-02
 */
public interface RecommendService extends IService<Recommend> {
    /***加载页面数据**/
    PageData getRecommendPageList(RecommendCo recommendCo);
}
