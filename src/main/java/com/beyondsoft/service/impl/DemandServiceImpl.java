package com.beyondsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.DemandCo;
import com.beyondsoft.entity.Demand;
import com.beyondsoft.mapper.DemandMapper;
import com.beyondsoft.service.DemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyondsoft.utils.Const;
import com.beyondsoft.utils.HttpResultEnum;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.DemandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-09-01
 */
@Service
@Transactional
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService {

    @Autowired
    private DemandMapper demandMapper;

    /**
     * 职位列表
     * @param demandCo
     * @return
     */
    @Override
    public PageData demandList(DemandCo demandCo) {
        Page<DemandVo> page = new Page<>(demandCo.getPage(),demandCo.getLimit());
        demandCo.setDelFlag(Const.DEL_FLAG_N);
        page.setRecords(demandMapper.demandList(demandCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 保存
     * @param demand
     * @return
     */
    @Override
    public Result saveDemand(Demand demand) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            demand.setDelFlag(Const.DEL_FLAG_N);
            demand.setCreateTime(new Date());
            Integer ret = this.baseMapper.insert(demand);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 修改
     * @param demand
     * @return
     */
    @Override
    public Result updateDemand(Demand demand) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<Demand> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", demand.getId());
            Integer ret = this.baseMapper.update(demand, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result deleteDemand(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(), HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<Demand> updateWrapper = new UpdateWrapper<Demand>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id", id);
            Demand demand = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(demand, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 开启招聘职位
     * @param id
     * @return
     */
    @Override
    public Result start(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<Demand> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("internal_recommend_status", Const.ENABLED_Y);
            Demand demand = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(demand, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 停止招聘职位
     * @param id
     * @return
     */
    @Override
    public Result stop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<Demand> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("internal_recommend_status", Const.ENABLED_N);
        Demand demand = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(demand, updateWrapper);
        if (ret != null && ret > 0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }
}
