package com.beyondsoft.service;

import com.beyondsoft.bo.PageData;
import com.beyondsoft.co.CustomerCo;
import com.beyondsoft.entity.BaCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyondsoft.utils.Result;
import com.beyondsoft.vo.BaCustomerAndProjectTreeVo;
import com.beyondsoft.vo.BaCustomerAndProjectVo;
import com.beyondsoft.vo.BaCustomerVo;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
public interface BaCustomerService extends IService<BaCustomer> {
    /**加载列表**/
    PageData listPageData(CustomerCo customerCo);
    /**添加 **/
    Result add(BaCustomerVo baCustomerVo);
    /**修改**/
    Result update(BaCustomerVo baCustomerVo);
    /**删除 **/
    Result delete(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**根据id查询得到 BaCustomerVo**/
    BaCustomerVo getBaCustomerVoById(Integer id);
    /**根据用户id查询当前的客户信息和项目组**/
    List<BaCustomerAndProjectVo> getCustomerAndProjectByUserId(Integer userId);
    /**根据用户id查询当前的客户信息和项目组生成树**/
    Object getCustomerAndProjectTreeByUserId(Integer userId);
    /**根据用户id查询当前的客户信息和项目组list**/
    List<BaCustomerAndProjectTreeVo> getCustomerAndProjectTreeListByUserId(Integer userId);
    /**根据用户id查询当前的first node**/
    Result getFirstNode(Integer userId);
    /** 根据部门ID取得客户 */
    List<BaCustomerVo> getCustomerByDeptId(Integer deptId);
    /** 根据客户负责人查询客户 */
    List<BaCustomer> getCustomerByChief(Integer chief);
    /**返回dtree的客户列表**/
    List<BaCustomerAndProjectTreeVo> getCustomersDtreeList();
    /**获得新增时的结点的排序数值**/
    Integer getAddCustomerSort(Integer pid);
    /**多级部门下拉列表**/
    Result getCustomerSelect();
//    /**客户移交**/
//    Result customerApplyTransfer(BaApplyTransferVo baApplyTransferVo);
}
