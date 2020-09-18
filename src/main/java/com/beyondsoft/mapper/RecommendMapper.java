package com.beyondsoft.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.co.RecommendCo;
import com.beyondsoft.entity.Recommend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.RecommendVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 推荐记录 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2020-09-02
 */
@Repository
public interface RecommendMapper extends BaseMapper<Recommend> {

    List<RecommendVo> getRecommendList(RecommendCo recommendCo, Page<RecommendVo> page);
}
