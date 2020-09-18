package com.beyondsoft.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.co.DemandCo;
import com.beyondsoft.entity.Demand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beyondsoft.vo.DemandVo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2020-09-01
 */
@Repository
public interface DemandMapper extends BaseMapper<Demand> {

    List<DemandVo> demandList(DemandCo demandCo,Page<DemandVo>page);
}
