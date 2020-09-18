package com.beyondsoft.service;

import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.DemandCo;
import com.beyondsoft.entity.Demand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kun
 * @since 2020-09-01
 */
public interface DemandService extends IService<Demand> {
    /**职位列表**/
    PageData demandList(DemandCo demandCo);

    Result saveDemand(Demand demand);

    Result updateDemand(Demand demand);

    Result deleteDemand(Integer id);

    Result start(Integer id);

    Result stop(Integer id);
}
